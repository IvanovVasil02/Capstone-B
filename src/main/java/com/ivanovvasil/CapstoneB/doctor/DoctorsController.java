package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.appointment.AppointmentsService;
import com.ivanovvasil.CapstoneB.appointment.payloads.FixAppointmentDTO;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.Prescription;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import com.ivanovvasil.CapstoneB.prescription.payloads.DoctorPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

  @Autowired
  AppointmentsService as;

  @GetMapping("")
  public List<DoctorDTO> getAll() {
    return ds.getAll().stream().map(doctor -> ds.convertToDoctorDTO(doctor)).toList();
  }

  @GetMapping("/patients")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public Page<PatientResponseDTO> getDoctorPatientsList(@AuthenticationPrincipal Doctor doctor,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "30") int size,
                                                        @RequestParam(defaultValue = "id") String orderBy) {
    return ps.getPatientsList(doctor, page, size, orderBy);
  }

  @GetMapping("/me")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public DoctorProfileDTO getDoctorsProfile(@AuthenticationPrincipal Doctor body) {
    return ds.convertToDoctorProfileDTO(body);
  }

  @GetMapping("/appointments")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public PageDTO getAppointments(@AuthenticationPrincipal Doctor doctor,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "30") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
    return as.getDoctorsAppointments(doctor, page, size, orderBy);
  }

  @GetMapping("/prescriptions")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public PageDTO getPrescriptions(@AuthenticationPrincipal Doctor doctor,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
    return prs.getDoctorPrescriptions(doctor, page, size, orderBy);
  }

  @GetMapping("/prescriptionsToApp")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public List<PrescriptionDTO> getPrescriptionsToApprove(@AuthenticationPrincipal Doctor doctor) {
    return prs.getPrescriptionsToApprove(doctor);
  }

  @PutMapping("/prescriptions/{prescriptionId}")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public Prescription ApprovePrescription(@AuthenticationPrincipal Doctor doctor, @PathVariable UUID prescriptionId, DoctorPrescriptionDTO body) {
    return prs.save(doctor.getId(), prescriptionId, body);
  }

  @PostMapping("/fixAppointment")
  @PreAuthorize("hasAuthority('DOCTOR')")
  public void fixAppointment(FixAppointmentDTO appointmentDTO) {
    as.fixAppointment(appointmentDTO);
  }
}
