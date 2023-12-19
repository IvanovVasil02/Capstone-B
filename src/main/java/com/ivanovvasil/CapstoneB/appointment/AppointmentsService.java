package com.ivanovvasil.CapstoneB.appointment;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
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

  public Page<AppointmentDTO> getDoctorsAppointments(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.findByDoctor(doctor, pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }

  public Page<AppointmentDTO> getPatientsAppointments(Patient patient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.findByPatient(patient, pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }


  public Page<AppointmentDTO> getPatientsAppointment(Patient currentPatient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.findByPatient(currentPatient, pageable);
    return appointmentPage.map(this::convertAppointmentToDTO);
  }

  public Page<AppointmentDTO> getDoctorAppointments(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> AppointmentPage = ar.findByDoctorId(doctor.getId(), pageable);
    return AppointmentPage.map(this::convertAppointmentToDTO);
  }

  public AppointmentDTO convertAppointmentToDTO(Appointment appointment) {
    return AppointmentDTO.builder()
            .id(appointment.getId())
            .date(appointment.getDate())
            .time(appointment.getTime())
            .doctor(ds.convertToDoctorDTO(appointment.getDoctor()))
            .patient(ps.convertPatientResponse(appointment.getPatient()))
            .build();
  }
}