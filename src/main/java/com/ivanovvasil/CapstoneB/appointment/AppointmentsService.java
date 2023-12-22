package com.ivanovvasil.CapstoneB.appointment;

import com.ivanovvasil.CapstoneB.appointment.payloads.AppointmentDTO;
import com.ivanovvasil.CapstoneB.appointment.payloads.FixAppointmentDTO;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.doctor.PageDTO;
import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
            .timeRequest(LocalDateTime.now())
            .status(AppointmentStatus.PENDING).build();
    ar.save(appointment);
  }

  public void fixAppointment(FixAppointmentDTO appointmentDTO) {
    Appointment appointment = ar.findById(appointmentDTO.id()).orElseThrow(() -> new NotFoundException(appointmentDTO.id()));
    appointment.setDate(appointmentDTO.date());
    appointment.setTime(appointmentDTO.time());
    ar.save(appointment);

  }

  public PageDTO getDoctorsAppointments(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.findByDoctor(doctor, pageable);
    long totalPending = ar.getAppointmentsToApprove(doctor.getId()).size();
    Page<AppointmentDTO> appointmentsPageDTO = appointmentPage.map(this::convertAppointmentToDTO);

    return new PageDTO(appointmentsPageDTO, totalPending);
  }

  public PageDTO getPatientsAppointment(Patient currentPatient, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Appointment> appointmentPage = ar.findByPatient(currentPatient, pageable);
    long totalPending = ar.getAppointmentsToApprove(currentPatient.getId()).size();
    Page<AppointmentDTO> appointmentsPageDTO = appointmentPage.map(this::convertAppointmentToDTO);
    return new PageDTO(appointmentsPageDTO, totalPending);
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