package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import jakarta.persistence.*;
import lombok.*;

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

}
