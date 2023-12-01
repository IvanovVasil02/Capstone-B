package com.ivanovvasil.CapstoneB.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepo extends JpaRepository<Prescription, UUID> {
  @Query("SELECT p FROM Prescription p WHERE patient.id = :id")
  List<Prescription> findAllByPatientId(UUID id);
}
