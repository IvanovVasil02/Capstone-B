package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
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
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.payloads.DoctorPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.MedicinePrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrescriptionsService {
  @Autowired
  PrescriptionRepo pr;
  @Autowired
  ASLService as;
  @Autowired
  MedicinesService ms;
  @Autowired
  PatientsService ps;
  @Autowired
  DoctorsService ds;
  @Autowired
  PrescriptionDetailsRepo prsd;

  public void save(Prescription prescription) {
    pr.save(prescription);
  }

  public Prescription save(UUID doctorId, UUID prescriptionId, DoctorPrescriptionDTO body) {
    Prescription prescription = this.findById(prescriptionId);

    if (prescription.getDoctor().getId().equals(doctorId)) {
      prescription.setIssuingDate(LocalDate.now());
      if (!body.priority().isEmpty()) {
        prescription.setPriorityPrescription(PriorityPrescription.valueOf(body.priority()));
      }
      if (!body.typeRecipe().isEmpty()) {
        prescription.setTypeRecipe(TypeRecipe.valueOf(body.typeRecipe()));
      }
      if (!body.prescription().isEmpty()) {
        Set<PrescriptionDetails> prescriptionDetailsSet = body.prescription();
        List<PrescriptionDetails> oldPrescriptionDetails = prsd.findAllByPrescriptionId(prescription.getId());
        oldPrescriptionDetails.forEach((item) -> prsd.deleteById(item.getId()));
        for (PrescriptionDetails prescriptionDetails : prescriptionDetailsSet) {
          Medicine medicine = ms.findById(prescriptionDetails.getMedicine().getId());
          prescriptionDetails.setMedicine(medicine);
          prescriptionDetails.setPrescription(prescription);
          prsd.save(prescriptionDetails);
        }
      }
      prescription.setStatus(PrescriptionStatus.APPROVED);
      return pr.save(prescription);
    } else {
      throw new UnauthorizedException("Permissions denied for this recipe");
    }

  }

  public void formatPrescription(Patient patient, MedicinePrescriptionDTO medicinePrescriptionDTO) {

    Set<PrescriptionDetails> prescriptionDetailsSet = medicinePrescriptionDTO.prescription();
    int packagingCount = prescriptionDetailsSet.stream().mapToInt(PrescriptionDetails::getQuantity).sum();

    Prescription prescription = Prescription.builder()
            .status(PrescriptionStatus.PENDING)
            .patient(patient)
            .prescription(medicinePrescriptionDTO.prescription())
            .packagesNumber(packagingCount)
            .region(patient.getMunicipality().getRegion())
            .provinceAbbr(patient.getMunicipality().getProvinceAbbr())
            .localHealthCode(patient.getHealthCompanyCode())
            .issuingDate(LocalDate.now())
            .doctor(patient.getDoctor())
            .build();
    this.save(prescription);

    for (PrescriptionDetails prescriptionDetails : prescriptionDetailsSet) {
      Medicine medicine = ms.findById(prescriptionDetails.getMedicine().getId());
      prescriptionDetails.setMedicine(medicine);
      prescriptionDetails.setPrescription(prescription);
      prsd.save(prescriptionDetails);
    }
  }

  public Prescription findById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Page<PrescriptionDTO> getPrescriptionsToApprove(Patient patient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Prescription> prescriptionList = pr.getPrescriptionsToApprovePat(patient.getId(), pageable);
    return prescriptionList.map(this::convertToPrescriptionDTO);
  }

  public Page<PrescriptionDTO> getPrescriptionsToApprove(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Prescription> prescriptionList = pr.getPrescriptionsToApproveDoc(doctor.getId(), pageable);
    return prescriptionList.map(this::convertToPrescriptionDTO);
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
            .isssuingDate(prescription.getIssuingDate())
            .status(prescription.getStatus())
            .build();
  }

  public PrescriptionDetailsDTO convertToPrescriptionDetailsDTO(PrescriptionDetails prescriptionDetails) {
    return PrescriptionDetailsDTO.builder()
            .id(prescriptionDetails.getId())
            .medicine(ms.convertMedicineToDTO(prescriptionDetails.getMedicine()))
            .quantity(prescriptionDetails.getQuantity())
            .build();
  }

  public void createPrescription(Doctor doctor, UUID patientId, DoctorPrescriptionDTO body) {
    Patient patient = ps.getPatientById(patientId);
    Set<PrescriptionDetails> prescriptionDetailsSet = body.prescription();
    int packagingCount = prescriptionDetailsSet.stream().mapToInt(PrescriptionDetails::getQuantity).sum();
    Prescription prescription = Prescription
            .builder()
            .issuingDate(LocalDate.now())
            .localHealthCode(patient.getHealthCompanyCode())
            .provinceAbbr(patient.getMunicipality().getProvinceAbbr())
            .doctor(doctor)
            .patient(patient)
            .status(PrescriptionStatus.APPROVED)
            .region(patient.getMunicipality().getRegion())
            .localHealthCode(patient.getHealthCompanyCode())
            .diagnosticQuestion(body.diagnosticQuestion())
            .packagesNumber(packagingCount)
            .build();
    this.save(prescription);

    if (body.typeRecipe() != null && !body.typeRecipe().isEmpty()) {
      System.out.println("TypeRecipe value: " + body.typeRecipe());
      prescription.setTypeRecipe(TypeRecipe.valueOf(body.typeRecipe()));
      this.save(prescription);
    }
    if (body.priority() != null && !body.priority().isEmpty()) {
      prescription.setPriorityPrescription(PriorityPrescription.valueOf(body.priority()));
      this.save(prescription);
    }

    for (PrescriptionDetails prescriptionDetails : prescriptionDetailsSet) {
      Medicine medicine = ms.findById(prescriptionDetails.getMedicine().getId());
      prescriptionDetails.setMedicine(medicine);
      prescriptionDetails.setPrescription(prescription);
      prsd.save(prescriptionDetails);
    }
  }

}
