package com.ivanovvasil.CapstoneB.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionDetailsRepo extends JpaRepository<PrescriptionDetails, UUID> {
  List<PrescriptionDetails> findAllByPrescriptionId(@Param("id") UUID id);

//  void deleteAllByPrescriptionId(UUID prescriptionId);

  @Modifying
  @Query("DELETE FROM PrescriptionDetails pd WHERE pd.prescription.id = :prescriptionId")
  void deleteByPrescriptionId(@Param("prescriptionId") UUID prescriptionId);

}
