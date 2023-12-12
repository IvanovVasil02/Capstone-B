package com.ivanovvasil.CapstoneB.municipality;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MunicipalityRepo extends JpaRepository<Municipality, UUID> {
  Boolean existsMunicipalityByCap(String code);
}
