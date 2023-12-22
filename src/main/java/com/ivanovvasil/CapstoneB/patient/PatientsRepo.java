package com.ivanovvasil.CapstoneB.patient;


import com.ivanovvasil.CapstoneB.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientsRepo extends JpaRepository<Patient, UUID> {
  Optional<Patient> findByEmailIgnoreCase(String email);

  List<Patient> findByDoctor(Doctor doctor);
  
  Page<Patient> findByFiscalCodeStartingWithIgnoreCase(String q, Pageable pageable);

  Page<Patient> findByNameStartingWithIgnoreCase(String q, Pageable pageable);
}
