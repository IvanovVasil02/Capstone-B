package com.ivanovvasil.CapstoneB.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientsController {
  @Autowired
  private PatientsService ps;

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public Patient getProfile(@AuthenticationPrincipal Patient currentPatient) {
    return currentPatient;
  }

}
