package com.ivanovvasil.CapstoneB.appointment;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, UUID> {
  Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);

  Page<Appointment> findByPatient(Patient patient, Pageable pageable);

  Page<Appointment> findByDoctorId(UUID id, Pageable pageable);
}
