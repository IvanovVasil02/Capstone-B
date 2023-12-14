package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import com.ivanovvasil.CapstoneB.prescription.payloads.PatientPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

  @GetMapping("/me/prescriptions")
  @ResponseStatus(HttpStatus.OK)
  public Page<PrescriptionDTO> getPrescriptions(@AuthenticationPrincipal Patient currentPatient,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "30") int size,
                                                @RequestParam(defaultValue = "id") String orderBy) {
    return prs.getPatientsPrescriptions(currentPatient, page, size, orderBy);
  }

  @PostMapping("/takePrescription")
  @ResponseStatus(HttpStatus.CREATED)
  public void prescriptionRequest(@AuthenticationPrincipal Patient currentUser, @RequestBody PatientPrescriptionDTO patientPrescriptionDTO) {
    System.out.println(patientPrescriptionDTO.toString());
    prs.formatPrescription(currentUser, patientPrescriptionDTO);
  }

}
