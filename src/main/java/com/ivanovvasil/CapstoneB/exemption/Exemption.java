package com.ivanovvasil.CapstoneB.exemption;

import com.ivanovvasil.CapstoneB.patient.Patient;
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
public class Exemption {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Patient patient;
  private String exemptionCode;
}
