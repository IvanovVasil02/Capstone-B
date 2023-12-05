package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.UserRole;
import jakarta.persistence.Entity;
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
public class Doctor extends User {
  private String professionalRegistrationNumber;
  private String medicalLicenseNumber;
  private LocalDate expirationOfLicense;
  private String emergencyContact;

  public Doctor(String name, String surname, LocalDate birthDate, String sex, String fiscalCode, String address, String email, String password, String phoneNumber, String province, String region, UserRole role, UUID id, String professionalRegistrationNumber, String medicalLicenseNumber, LocalDate expirationOfLicense, String emergencyContact) {
    super(name, surname, birthDate, sex, fiscalCode, address, email, password, phoneNumber, province, region, role, id);
    this.professionalRegistrationNumber = professionalRegistrationNumber;
    this.medicalLicenseNumber = medicalLicenseNumber;
    this.expirationOfLicense = expirationOfLicense;
    this.emergencyContact = emergencyContact;
  }
}
