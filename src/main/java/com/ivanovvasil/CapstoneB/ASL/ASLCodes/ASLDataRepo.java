package com.ivanovvasil.CapstoneB.ASL.ASLCodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ASLDataRepo extends JpaRepository<ASL, UUID> {
  ASL findByRegionDenominationLike(String region);

  @Query("SELECT a FROM ASL a WHERE a.municipalityDenomination = :municipality")
  ASL findByMunicipality(String municipality);
}
