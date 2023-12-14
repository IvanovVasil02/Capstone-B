package com.ivanovvasil.CapstoneB.Medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MedicineRepo extends JpaRepository<Medicine, UUID> {
  Page<Medicine> findByActiveIngredientIgnoreCaseContaining(String search, Pageable pageable);

  @Query("SELECT m FROM Medicine m WHERE LOWER(m.nameAndPackaging) LIKE LOWER(CONCAT('%', :search, '%'))")
  Page<Medicine> findByName(String search, Pageable pageable);
}
