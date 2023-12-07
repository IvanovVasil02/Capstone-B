package com.ivanovvasil.CapstoneB.Medicine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicineRepo extends JpaRepository<Medicine, UUID> {
}
