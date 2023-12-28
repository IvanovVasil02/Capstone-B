package com.ivanovvasil.CapstoneB.patient;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientsRepo extends JpaRepository<Patient, UUID> {
  Optional<Patient> findByEmailIgnoreCase(String email);

  Page<Patient> findByDoctorId(UUID id, Pageable pageable);

  Page<Patient> findByFiscalCodeStartingWithIgnoreCaseAndDoctorId(String q, UUID doctor, Pageable pageable);

  Page<Patient> findByNameStartingWithIgnoreCaseAndDoctorId(String q, UUID doctor, Pageable pageable);
}
