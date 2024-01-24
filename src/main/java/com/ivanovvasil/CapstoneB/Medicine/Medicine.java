package com.ivanovvasil.CapstoneB.Medicine;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "medicines")
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
  private String medicineName;
  private String publicPrice;
  private String holderOfMarketingAuthorization;
  private String identificationCode;
  private String xInAifaTransparencyList;
  private String xInRegionList;
  private String cubicMetersOxygen;
  @OneToMany(mappedBy = "medicine")
  private Set<PrescriptionDetails> prescriptionList = new HashSet<>();

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Medicine medicine = (Medicine) object;
    return Objects.equals(id, medicine.id);
  }

  @Override
  public String toString() {
    return "Medicine{" +
            "medicineName='" + medicineName + '\'' +
            ", publicPrice='" + publicPrice + '\'' +
            '}';
  }
}
