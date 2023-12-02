package com.ivanovvasil.CapstoneB.ASL.exemption;

import com.ivanovvasil.CapstoneB.patient.Patient;
import jakarta.persistence.*;
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
public class Exemption {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @OneToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;
  private String exemptionCode;
}
