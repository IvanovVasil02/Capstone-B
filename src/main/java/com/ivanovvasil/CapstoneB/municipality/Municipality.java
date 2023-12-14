package com.ivanovvasil.CapstoneB.municipality;

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
public class Municipality {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String region;
  private String province;
  private String municipality;
  private String postalCode;
  private String istat;
}
