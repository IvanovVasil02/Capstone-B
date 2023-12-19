package com.ivanovvasil.CapstoneB.appointment;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

  @Autowired
  AppointmentsService as;

  @GetMapping("/doctor")
  @PreAuthorize("hasAuthority('DOCTOR')")
  @ResponseStatus(HttpStatus.OK)
  public Page<AppointmentDTO> getPrescriptions(@AuthenticationPrincipal Doctor currentDoctor,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size,
                                               @RequestParam(defaultValue = "id") String orderBy) {
    return as.getDoctorsAppointments(currentDoctor, page, size, orderBy);
  }

  @GetMapping("/patient")
  @PreAuthorize("hasAuthority('PATIENT')")
  @ResponseStatus(HttpStatus.OK)
  public Page<AppointmentDTO> getPrescriptions(@AuthenticationPrincipal Patient currentPatient,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size,
                                               @RequestParam(defaultValue = "id") String orderBy) {
    return as.getPatientsAppointments(currentPatient, page, size, orderBy);
  }
}
