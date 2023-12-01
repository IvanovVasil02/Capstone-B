package com.ivanovvasil.CapstoneB.patient;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientsRepo extends JpaRepository<Patient, UUID> {
  Optional<Patient> findByEmailIgnoreCase(String email);
}