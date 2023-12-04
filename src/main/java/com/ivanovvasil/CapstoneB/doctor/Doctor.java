package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.user.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor extends User {
  private String professionalRegistrationNumber;
  private String medicalLicenseNumber;
  private LocalDate expirationOfLicense;
  private String emergencyContact;
}
