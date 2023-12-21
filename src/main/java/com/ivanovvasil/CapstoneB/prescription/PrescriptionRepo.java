package com.ivanovvasil.CapstoneB.prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepo extends JpaRepository<Prescription, UUID> {
  @Query("SELECT p FROM Prescription p WHERE p.patient.id = :id")
  Page<Prescription> findAllByPatientId(UUID id, Pageable pageable);

  List<Prescription> findAllByPatientId(UUID id);

  @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :id and p.status = 'IN_ATTESA'")
  List<Prescription> getPrescriptionsToApproveDoc(@Param("id") UUID id);

  @Query("SELECT p FROM Prescription p WHERE p.patient.id = :id and p.status = 'IN_ATTESA'")
  List<Prescription> getPrescriptionsToApprovePat(@Param("id") UUID id);

  Page<Prescription> findByDoctorId(UUID id, Pageable pageable);
}
