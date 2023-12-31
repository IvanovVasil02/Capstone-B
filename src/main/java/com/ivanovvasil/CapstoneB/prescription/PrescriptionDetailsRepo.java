package com.ivanovvasil.CapstoneB.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionDetailsRepo extends JpaRepository<PrescriptionDetails, UUID> {
  List<PrescriptionDetails> findAllByPrescriptionId(UUID id);
}
