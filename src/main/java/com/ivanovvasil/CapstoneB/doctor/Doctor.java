package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.UserRole;
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

  public Doctor(String name, String surname, LocalDate birthDate, String sex, String address, String email, String password, String phoneNumber, String municipality, String region, String professionalRegistrationNumber, String medicalLicenseNumber, LocalDate expirationOfLicense, String emergencyContact, UserRole role) {
    super(name, surname, birthDate, sex, address, email, password, phoneNumber, municipality, region, role);
    this.professionalRegistrationNumber = professionalRegistrationNumber;
    this.medicalLicenseNumber = medicalLicenseNumber;
    this.expirationOfLicense = expirationOfLicense;
    this.emergencyContact = emergencyContact;
  }

}
