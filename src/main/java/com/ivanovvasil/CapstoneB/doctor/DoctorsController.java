package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.Prescription;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import com.ivanovvasil.CapstoneB.prescription.payloads.DoctorPrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {
  @Autowired
  DoctorsService ds;
  @Autowired
  PatientsService ps;
  @Autowired
  PrescriptionsService prs;

  @GetMapping("/patients")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public List<PatientResponseDTO> getDoctorPatientsList(@AuthenticationPrincipal Doctor body) {
    return ps.getPatientsList(body);
  }

  @GetMapping("/prescriptionsToApp")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public List<Prescription> getPrescriptionsToApprove(@AuthenticationPrincipal Doctor doctor) {
    return prs.getPrescriptionsToApprove(doctor);
  }

  @PutMapping("/prescriptions/{prescriptionId}")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public Prescription ApprovePrescription(@AuthenticationPrincipal Doctor doctor, @PathVariable UUID prescriptionId, DoctorPrescriptionDTO body) {
    return prs.save(doctor.getId(), prescriptionId, body);
  }
}
