package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {
  @Autowired
  DoctorsService ds;
  @Autowired
  PatientsService ps;

  @GetMapping("/patients")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public List<PatientResponseDTO> getDoctorPatientsList(@AuthenticationPrincipal Doctor body) {
    return ps.getPatientsList(body);
  }
}
