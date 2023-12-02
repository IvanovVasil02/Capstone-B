package com.ivanovvasil.CapstoneB.Medicine;

import com.ivanovvasil.CapstoneB.prescription.Prescription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
  @ManyToMany(mappedBy = "prescription")
  private List<Prescription> prescriptionList;
}
