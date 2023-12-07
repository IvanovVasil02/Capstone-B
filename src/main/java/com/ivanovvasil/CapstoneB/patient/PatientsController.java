package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import com.ivanovvasil.CapstoneB.prescription.payloads.FormattedPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PatientPrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientsController {
  @Autowired
  private PatientsService ps;

  @Autowired
  private PrescriptionsService prs;

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public PatientResponseDTO getProfile(@AuthenticationPrincipal Patient currentPatient) {
    return ps.convertPatientResponse(currentPatient);
  }

  @PostMapping("/takePrescription")
  @ResponseStatus(HttpStatus.CREATED)
  public FormattedPrescriptionDTO prescriptionRequest(@AuthenticationPrincipal Patient currentUser, @RequestBody PatientPrescriptionDTO patientPrescriptionDTO) {
    System.out.println(patientPrescriptionDTO.toString());
    return prs.formatPrescription(currentUser, patientPrescriptionDTO);
  }

}
