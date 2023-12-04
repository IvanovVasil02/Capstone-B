package com.ivanovvasil.CapstoneB.ASL.exemption;

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
  private String exemptionCode;
  private String type;
}
