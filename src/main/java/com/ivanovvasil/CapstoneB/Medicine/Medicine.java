package com.ivanovvasil.CapstoneB.Medicine;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.rmi.server.UID;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Medicine {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String activeIngredient;
  private String groupDescription;
  private String nameAndPackaginf;
  private String publicPrice;
  private String holderOfMarketingAuthorization;
  private String MarketingAuthorization;
  private String xInAifaTransparencyList;
  private String xInRegionList;
  private String cubicMetersOxygen;
}
