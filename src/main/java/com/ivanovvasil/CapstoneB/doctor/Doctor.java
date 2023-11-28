package com.ivanovvasil.CapstoneB.doctor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String fullName;
  private String phoneNumber;
  private String email;
  private String professionalRegistrationNumber;
  private String medicalLicenseNumber;
  private LocalDate expirationOfLicense;
  private String emergencyContact;
}
