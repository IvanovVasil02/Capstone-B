package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.payloads.DoctorPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.FormattedPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PatientPrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionsService {
  @Autowired
  PrescriptionRepo pr;
  @Autowired
  ASLService as;

  public Prescription save(DoctorPrescriptionDTO body) {
    Prescription prescription = this.finddById(body.id());
    prescription.setIsssuingDate(LocalDate.now());
    prescription.setNote(body.note());
    prescription.setPriorityPrescription(PriorityPrescription.valueOf(body.priority()));
    prescription.setTypeRecipe(TypeRecipe.valueOf(body.typeRecipe()));
    prescription.setLocalHealthCode(as.getAslCodeByRegionName(prescription.getRegion()));
    prescription.setDoctor(prescription.getPatient().getDoctor());
    return pr.save(prescription);
  }

  public Prescription save(Prescription prescription) {
    return pr.save(prescription);
  }

  public FormattedPrescriptionDTO formatPrescription(Patient patient, PatientPrescriptionDTO prescriptionDTO) {

    return FormattedPrescriptionDTO
            .builder()
            .patient(patient)
            .prescription(prescriptionDTO.prescription())
            .packagesNumber(prescriptionDTO.packagesNumber())
            .region(patient.getDoctor().getRegion())
            .build();
  }

  public Prescription finddById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Prescription> finddAllPrescriptionById(UUID id) {
    return pr.findAllByPatientId(id);
  }

  public void delete(UUID id) {
    pr.deleteById(id);
  }


}
