package com.ivanovvasil.CapstoneB.Medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicineRepo extends JpaRepository<Medicine, UUID> {
  Page<Medicine> findByActiveIngredientIgnoreCaseContaining(String search, Pageable pageable);

  Page<Medicine> findByMedicineNameContainingIgnoreCase(String search, Pageable pageable);
}
