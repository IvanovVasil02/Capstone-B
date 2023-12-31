package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.appointment.AppointmentsService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.payloads.PageDTO;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import com.ivanovvasil.CapstoneB.prescription.payloads.PatientPrescriptionDTO;
import com.ivanovvasil.CapstoneB.prescription.payloads.PrescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientsController {
  @Autowired
  private PatientsService ps;

  @Autowired
  private PrescriptionsService prs;
  @Autowired
  private AppointmentsService aps;

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  public PatientResponseDTO getProfile(@AuthenticationPrincipal Patient currentPatient) {
    return ps.convertPatientResponse(currentPatient);
  }

  @GetMapping("/search")
  public Page<PatientResponseDTO> SearchMedicineByActiveIngredient(@AuthenticationPrincipal Doctor doctor,
                                                                   @RequestParam String q,
                                                                   @RequestParam String by,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "30") int size,
                                                                   @RequestParam(defaultValue = "id") String orderBy) {
    return ps.searchPatients(q, doctor, by, page, size, orderBy);
  }

  @GetMapping("/prescriptions")
  @ResponseStatus(HttpStatus.OK)
  public PageDTO getPrescriptions(@AuthenticationPrincipal Patient currentPatient,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "30") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
    return prs.getPatientsPrescriptions(currentPatient, page, size, orderBy);
  }

  @GetMapping("/prescriptionsToApp")
  @PreAuthorize("hasAuthority('PATIENT')")
  public Page<PrescriptionDTO> getPrescriptionsToApprove(@AuthenticationPrincipal Patient patient,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "20") int size,
                                                         @RequestParam(defaultValue = "id") String orderBy) {
    return prs.getPatientPrescriptionsToApprove(patient, page, size, orderBy);
  }

  @GetMapping("/appointments")
  @ResponseStatus(HttpStatus.OK)
  public PageDTO getAppointments(@AuthenticationPrincipal Patient currentPatient,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "30") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
    return aps.getPatientsAppointment(currentPatient, page, size, orderBy);
  }

  @PostMapping("/takePrescription")
  @ResponseStatus(HttpStatus.CREATED)
  public void prescriptionRequest(@AuthenticationPrincipal Patient currentUser, @RequestBody PatientPrescriptionDTO patientPrescriptionDTO) {
    prs.formatPrescription(currentUser, patientPrescriptionDTO);
  }

  @PostMapping("/askAppointment")
  @ResponseStatus(HttpStatus.OK)
  public void askAppointment(@AuthenticationPrincipal Patient patient) {
    aps.askAppointment(patient);
  }

}
