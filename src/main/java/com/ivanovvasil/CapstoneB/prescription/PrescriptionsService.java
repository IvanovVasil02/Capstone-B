package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.Medicine.MedicinesService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.doctor.payloads.PageDTO;
import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionVerificationStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.payloads.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrescriptionsService {
  @Autowired
  PrescriptionRepo pr;
  @Autowired
  MedicinesService ms;
  @Autowired
  PatientsService ps;
  @Autowired
  DoctorsService ds;
  @Autowired
  PrescriptionDetailsRepo prsd;

  public Prescription save(Prescription prescription) {
    return pr.save(prescription);
  }


  @Transactional
  public void save(UUID doctorId, UUID prescriptionId, DoctorPrescriptionDTO body) {
    Prescription prescription = this.findById(prescriptionId);
    if (prescription.getDoctor().getId().equals(doctorId)) {
      prescription.setIssuingDate(LocalDateTime.now());
      if (!body.priority().isEmpty()) {
        prescription.setPriorityPrescription(PriorityPrescription.valueOf(body.priority()));
      }
      if (!body.typeRecipe().isEmpty()) {
        prescription.setTypeRecipe(TypeRecipe.valueOf(body.typeRecipe()));
      }
      if (!body.prescription().isEmpty()) {
        List<PrescriptionDetails> oldPrescription = prsd.findAllByPrescriptionId(prescriptionId);
        System.out.println(prescriptionId);
        prsd.deleteByPrescriptionId(prescriptionId);

        for (PrescriptionDetails prescriptionDetails : body.prescription()) {
          Medicine medicine = ms.findById(prescriptionDetails.getMedicine().getId());
          PrescriptionDetails newPrescriptionDetails = PrescriptionDetails.builder()
                  .prescription(prescription)
                  .medicine(medicine)
                  .note(prescriptionDetails.getNote())
                  .quantity(prescriptionDetails.getQuantity())
                  .build();
          prsd.save(newPrescriptionDetails);
        }
      }
      prescription.setStatus(PrescriptionStatus.APPROVED);
      prescription.setVerificationStatus(PrescriptionVerificationStatus.VERIFIED);
      pr.save(prescription);


    } else {
      throw new UnauthorizedException("Permissions denied for this recipe");
    }

  }

  public void createPrescription(Doctor doctor, UUID patientId, DoctorPrescriptionDTO body) {
    Patient patient = ps.getPatientById(patientId);
    List<PrescriptionDetails> prescriptionDetailsSet = body.prescription();
    int packagingCount = prescriptionDetailsSet.stream().mapToInt(PrescriptionDetails::getQuantity).sum();
    Prescription prescription = Prescription
            .builder()
            .issuingDate(LocalDateTime.now())
            .localHealthCode(patient.getHealthCompanyCode())
            .provinceAbbr(patient.getMunicipality().getProvinceAbbr())
            .doctor(doctor)
            .patient(patient)
            .status(PrescriptionStatus.APPROVED)
            .verificationStatus(PrescriptionVerificationStatus.VERIFIED)
            .region(patient.getMunicipality().getRegion())
            .localHealthCode(patient.getHealthCompanyCode())
            .diagnosticQuestion(body.diagnosticQuestion())
            .packagesNumber(packagingCount)
            .build();
    this.save(prescription);

    if (StringUtils.isNotEmpty(body.typeRecipe())) {
      prescription.setTypeRecipe(TypeRecipe.valueOf(body.typeRecipe()));
    }
    if (StringUtils.isNotEmpty(body.priority())) {
      prescription.setPriorityPrescription(PriorityPrescription.valueOf(body.priority()));
    }

    for (PrescriptionDetails prescriptionDetails : prescriptionDetailsSet) {
      Medicine medicine = ms.findById(prescriptionDetails.getMedicine().getId());
      prescriptionDetails.setMedicine(medicine);
      prescriptionDetails.setPrescription(prescription);
      prsd.save(prescriptionDetails);
    }
    this.save(prescription);
  }


  public void formatPrescription(Patient patient, MedicinePrescriptionDTO medicinePrescriptionDTO) {

    List<PrescriptionDetails> prescriptionDetailsList = medicinePrescriptionDTO.prescription();
    int packagingCount = prescriptionDetailsList.stream().mapToInt(PrescriptionDetails::getQuantity).sum();

    Prescription prescription = Prescription.builder()
            .status(PrescriptionStatus.PENDING)
            .patient(patient)
            .prescription(medicinePrescriptionDTO.prescription())
            .packagesNumber(packagingCount)
            .region(patient.getMunicipality().getRegion())
            .provinceAbbr(patient.getMunicipality().getProvinceAbbr())
            .localHealthCode(patient.getHealthCompanyCode())
            .issuingDate(LocalDateTime.now())
            .doctor(patient.getDoctor())
            .build();
    this.save(prescription);

    for (PrescriptionDetails prescriptionDetails : prescriptionDetailsList) {
      Medicine medicine = ms.findById(prescriptionDetails.getMedicine().getId());
      prescriptionDetails.setMedicine(medicine);
      prescriptionDetails.setPrescription(prescription);
      prsd.save(prescriptionDetails);
    }

    List<Prescription> patientPrescriptions = patient.getPrescriptions();
    this.verifyPrsecription(patientPrescriptions, prescription);
  }

  public Prescription findById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Page<PrescriptionDTO> getPrescriptionsToApprove(Patient patient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Prescription> prescriptionList = pr.getPrescriptionsToApprovePat(patient.getId(), pageable);
    return prescriptionList.map(this::convertToPrescriptionDTO);
  }

  public PendingPrescriptionsDTO getPrescriptionsToApprove(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    List<Prescription> pendingPrescriptions = pr.getPrescriptionsToApproveDoc(doctor.getId());

    List<PrescriptionDTO> noVerifiedPrescriptions = pendingPrescriptions.stream()
            .filter(prescription -> !this.isVerifiedPrescription(prescription))
            .map(this::convertToPrescriptionDTO)
            .toList();

    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), noVerifiedPrescriptions.size());
    Page<PrescriptionDTO> noVerifiedPrescriptionPage = new PageImpl<>(noVerifiedPrescriptions.subList(start, end), pageable, noVerifiedPrescriptions.size());

    List<PrescriptionDTO> verifiedPrescriptions = pendingPrescriptions.stream()
            .filter(this::isVerifiedPrescription)
            .map(this::convertToPrescriptionDTO)
            .toList();
    end = Math.min((start + pageable.getPageSize()), verifiedPrescriptions.size());
    Page<PrescriptionDTO> verifiedPrescriptionPage = new PageImpl<>(verifiedPrescriptions.subList(start, end), pageable, verifiedPrescriptions.size());

    return new PendingPrescriptionsDTO(noVerifiedPrescriptionPage, verifiedPrescriptionPage);
  }

  public Page<PrescriptionDTO> getPatientPrescriptionsToApprove(Patient patient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Prescription> prescriptionList = pr.getPrescriptionsToApprovePat(patient.getId(), pageable);
    return prescriptionList.map(this::convertToPrescriptionDTO);
  }

  public void delete(UUID id) {
    pr.deleteById(id);
  }

  public PageDTO getDoctorPrescriptions(Doctor doctor, int page, int size, String order) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(order));
    Page<Prescription> prescriptionPage = pr.findByDoctorId(doctor.getId(), pageable);
    long totalPending = pr.getPrescriptionsToApproveDoc(doctor.getId()).size();
    Page<PrescriptionDTO> prescriptionDTOS = prescriptionPage.map(this::convertToPrescriptionDTO);
    return new PageDTO(prescriptionDTOS, totalPending);
  }

  public PageDTO getPatientsPrescriptions(Patient currentPatient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Prescription> prescriptionPage = pr.findAllByPatientId(currentPatient.getId(), pageable);
    long totalPending = pr.getPrescriptionsToApprovePat(currentPatient.getId()).size();
    Page<PrescriptionDTO> prescriptionDTOS = prescriptionPage.map(this::convertToPrescriptionDTO);
    return new PageDTO(prescriptionDTOS, totalPending);
  }


  public void approveMultiplePrescriptions(UUID[] prescriptions) {
    Arrays.stream(prescriptions).forEach((prescriptionId) -> {
      Prescription prescription = this.findById(prescriptionId);
      prescription.setStatus(PrescriptionStatus.APPROVED);
      this.save(prescription);
    });
  }

  public void verifyPrsecription(List<Prescription> patientPrescriptions, Prescription prescription) {
    boolean foundEqual = false;

    for (Prescription oldPrescription : patientPrescriptions) {
      if (oldPrescription.equals(prescription)) {
        prescription.setVerificationStatus(PrescriptionVerificationStatus.VERIFIED);
        foundEqual = true;
        break;
      }
    }

    if (!foundEqual) {
      prescription.setVerificationStatus(PrescriptionVerificationStatus.NOT_VERIFIED);
    }
    this.save(prescription);
  }

  public Boolean isVerifiedPrescription(Prescription prescription) {
    return prescription.getVerificationStatus() == PrescriptionVerificationStatus.VERIFIED;
  }

  public PrescriptionDTO convertToPrescriptionDTO(Prescription prescription) {
    Set<PrescriptionDetailsDTO> prescriptionDetailsDTOList = prescription.getPrescription()
            .stream().map((this::convertToPrescriptionDetailsDTO)).collect(Collectors.toSet());
    return PrescriptionDTO.builder()
            .prescriptionID(prescription.getId())
            .patient(ps.convertPatientResponse(prescription.getPatient()))
            .doctor(ds.convertToDoctorDTO(prescription.getDoctor()))
            .prescription(prescriptionDetailsDTOList)
            .packagesNumber(prescription.getPackagesNumber())
            .region(prescription.getRegion())
            .provinceAbbr(prescription.getProvinceAbbr())
            .localHealthCode(prescription.getLocalHealthCode())
            .issuingDate(prescription.getIssuingDate())
            .status(prescription.getStatus())
            .verificationStatus(prescription.getVerificationStatus())
            .build();
  }

  public PrescriptionDetailsDTO convertToPrescriptionDetailsDTO(PrescriptionDetails prescriptionDetails) {
    return PrescriptionDetailsDTO.builder()
            .id(prescriptionDetails.getId())
            .medicine(ms.convertMedicineToDTO(prescriptionDetails.getMedicine()))
            .quantity(prescriptionDetails.getQuantity())
            .build();
  }
}
