package com.ivanovvasil.CapstoneB.municipality;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MunicipalityRepo extends JpaRepository<Municipality, UUID> {

  Municipality findFirstByIstat(String code);

  Boolean existsMunicipalityByPostalCode(String code);


  Municipality findFirstByPostalCode(String code);
}
