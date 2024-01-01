package com.ivanovvasil.CapstoneB.appointment;

import com.ivanovvasil.CapstoneB.appointment.payloads.AppointmentDTO;
import com.ivanovvasil.CapstoneB.appointment.payloads.FixAppointmentDTO;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AppointmentsService {

  @Autowired
  AppointmentRepo ar;

  @Autowired
  DoctorsService ds;
  @Autowired
  PatientsService ps;

  public void save(Appointment appointment) {
    ar.save(appointment);
  }

  public void askAppointment(Patient patient) {
    Appointment appointment = Appointment.builder()
            .patient(patient)
            .doctor(patient.getDoctor())
            .status(AppointmentStatus.PENDING).build();
    ar.save(appointment);
  }

  public void fixAppointment(FixAppointmentDTO appointmentDTO) {
    Appointment appointment = ar.findById(appointmentDTO.id()).orElseThrow(() -> new NotFoundException(appointmentDTO.id()));
    appointment.setDate(appointmentDTO.date());
    appointment.setTime(appointmentDTO.time());
    appointment.setStatus(AppointmentStatus.ACCEPTED);
    ar.save(appointment);
  }

  public Page<AppointmentDTO> getDoctorsAppointments(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.getAcceptedDoctorAppointments(doctor.getId(), pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }

  public Page<AppointmentDTO> getDoctorsAppointmentsToAccept(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.getAppointmentsToApproveDoc(doctor.getId(), pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }

  public Page<AppointmentDTO> getPatientsAppointment(Patient patient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.getAcceptedPatientAppointments(patient.getId(), pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }

  public Page<AppointmentDTO> getPatientsAppointmentsToAccept(Patient patient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.getAppointmentsToApprovePat(patient.getId(), pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }

  public AppointmentDTO convertAppointmentToDTO(Appointment appointment) {
    return AppointmentDTO.builder()
            .id(appointment.getId())
            .date(appointment.getDate())
            .time(appointment.getTime())
            .doctor(ds.convertToDoctorDTO(appointment.getDoctor()))
            .patient(ps.convertPatientResponse(appointment.getPatient()))
            .status(String.valueOf(appointment.getStatus()))
            .build();
  }


}