package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.ASL.exemption.Exemption;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "PatientsBuilder")
public class Patient extends User {
  @ManyToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;
  private String healthCompanyCode;
  @ManyToMany
  @JoinTable(
          name = "patients_exemptions",
          joinColumns = @JoinColumn(name = "patient_id"),
          inverseJoinColumns = @JoinColumn(name = "exemption_id"))
  private List<Exemption> exemptions;

  public Patient(String name, String surname, LocalDate birthDate, String sex, String address, String email, String password, String phoneNumber, String municipality, String region, UserRole role, Doctor doctor) {
    super(name, surname, birthDate, sex, address, email, password, phoneNumber, municipality, region, role);
    this.doctor = doctor;
  }

  @Override
  public String toString() {
    return "Patient{" +
            "doctor=" + doctor.getName() +
            ", healthCompanyCode='" + healthCompanyCode + '\'' +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", birthDate=" + birthDate +
            ", sex='" + sex + '\'' +
            ", fiscalCode='" + fiscalCode + '\'' +
            ", address='" + address + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", municipality='" + municipality + '\'' +
            ", region='" + region + '\'' +
            ", role=" + role +
            "} " + super.toString();
  }
}
