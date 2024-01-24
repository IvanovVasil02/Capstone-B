package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "prescriptions_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PrescriptionDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "medicine_id")
  private Medicine medicine;
  @ManyToOne
  @JoinColumn(name = "prescription_id")
  private Prescription prescription;
  private int quantity;
  private String note;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    PrescriptionDetails that = (PrescriptionDetails) object;
    return quantity == that.quantity && Objects.equals(medicine, that.medicine) && Objects.equals(note, that.note);
  }

  @Override
  public String toString() {
    return "PrescriptionDetails{" +
            id + '\'' +
            "medicine=" + medicine +
            ", quantity=" + quantity +
            ", note='" + note + '\'' +
            '}';
  }
}
