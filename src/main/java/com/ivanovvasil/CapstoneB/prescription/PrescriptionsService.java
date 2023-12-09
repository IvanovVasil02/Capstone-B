package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.Medicine.MedicinesService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.payloads.DoctorPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PatientPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

  public Prescription save(UUID doctorId, UUID prescriptionId, DoctorPrescriptionDTO body) {
    Prescription prescription = this.findById(prescriptionId);
    if (prescription.getDoctor().getId().equals(doctorId)) {
      prescription.setIssuingDate(LocalDate.now());
      prescription.setNote(body.note());
      prescription.setLocalHealthCode(prescription.getPatient().getHealthCompanyCode());
      if (body.priority() != null) {
        prescription.setPriorityPrescription(PriorityPrescription.valueOf(body.priority()));
      }
      if (body.typeRecipe() != null) {
        prescription.setTypeRecipe(TypeRecipe.valueOf(body.typeRecipe()));
      }
      if (!prescription.getPrescription().isEmpty()) {
        prescription.setPrescription(prescription.getPrescription());
      }
      return pr.save(prescription);
    } else {
      throw new UnauthorizedException("Permissions denied for this recipe");
    }

  }

  public void save(Prescription prescription) {
    pr.save(prescription);
  }

  public void formatPrescription(Patient patient, PatientPrescriptionDTO patientPrescriptionDTO) {

    Set<PrescriptionDetails> prescriptionDetailsSet = patientPrescriptionDTO.prescription();
    int packagingCount = prescriptionDetailsSet.stream().mapToInt(PrescriptionDetails::getQuantity).sum();

    Prescription prescription = Prescription.builder()
            .status(PrescriptionStatus.IN_ATTESA)
            .patient(patient)
            .prescription(patientPrescriptionDTO.prescription())
            .packagesNumber(packagingCount)
            .region(patient.getRegion())
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

  public List<Prescription> finddAllPrescriptionById(UUID id) {
    return pr.findAllByPatientId(id);
  }

  public List<PrescriptionDTO> getPrescriptionsToApprove(Doctor doctor) {
    List<Prescription> prescriptionList = pr.getPrescriptionsToApprove(doctor.getId());
    return prescriptionList.stream().map(this::convertToPrescriptionDTO).collect(Collectors.toList());
  }

  public void delete(UUID id) {
    pr.deleteById(id);
  }


  public List<Prescription> getPrescriptions(Doctor doctor) {
    return pr.findByDoctorId(doctor.getId());
  }

  public PrescriptionDetailsDTO convertToPrescriptionDetailsDTO(PrescriptionDetails prescriptionDetails) {
    return PrescriptionDetailsDTO.builder()
            .id(prescriptionDetails.getId())
            .medicine(ms.convertMedicineToDTO(prescriptionDetails.getMedicine()))
            .quantity(prescriptionDetails.getQuantity())
            .build();
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
            .localHealthCode(prescription.getLocalHealthCode())
            .status(prescription.getStatus())
            .build();
  }
}
