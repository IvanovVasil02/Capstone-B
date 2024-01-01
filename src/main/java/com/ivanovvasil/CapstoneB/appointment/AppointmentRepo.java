package com.ivanovvasil.CapstoneB.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, UUID> {
  @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :id AND a.status = 'PENDING'")
  Page<Appointment> getAppointmentsToApproveDoc(@Param("id") UUID id, Pageable pageable);

  @Query("SELECT a FROM Appointment a WHERE a.patient.id = :id AND a.status = 'PENDING'")
  Page<Appointment> getAppointmentsToApprovePat(@Param("id") UUID id, Pageable pageable);

  @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :id AND a.status = 'ACCEPTED'")
  Page<Appointment> getAcceptedDoctorAppointments(@Param("id") UUID id, Pageable pageable);

  @Query("SELECT a FROM Appointment a WHERE a.patient.id = :id AND a.status = 'ACCEPTED'")
  Page<Appointment> getAcceptedPatientAppointments(@Param("id") UUID id, Pageable pageable);
}
