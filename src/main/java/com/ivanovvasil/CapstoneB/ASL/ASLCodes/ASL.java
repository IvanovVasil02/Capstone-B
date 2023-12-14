package com.ivanovvasil.CapstoneB.ASL.ASLCodes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ASL {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String regionCode;
  private String regionDenomination;
  private String companyCode;
  private String companyDenomination;
  private String municipalityIstat;
  private String municipalityDenomination;
}
