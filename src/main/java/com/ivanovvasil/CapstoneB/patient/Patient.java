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
@Builder
public class Patient extends User {

  @OneToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;
  private String healthCompanyCode;
  @ManyToMany
  @JoinTable(
          name = "patients_exemptions",
          joinColumns = @JoinColumn(name = "patient_id"),
          inverseJoinColumns = @JoinColumn(name = "exemption_id"))
  private List<Exemption> exemptions;

  public Patient(String name, String surname, LocalDate birthDate, String sex, String fiscalCode, String address, String email, String password, String phoneNumber, String municipality, String region, UserRole role, Doctor doctor, String healthCompanyCode) {
    super(name, surname, birthDate, sex, fiscalCode, address, email, password, phoneNumber, municipality, region, role);
    this.doctor = doctor;
    this.healthCompanyCode = healthCompanyCode;
  }
  
}
