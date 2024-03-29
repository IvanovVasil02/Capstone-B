package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.ASL.exemption.Exemption;
import com.ivanovvasil.CapstoneB.appointment.Appointment;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.municipality.Municipality;
import com.ivanovvasil.CapstoneB.prescription.Prescription;
import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "PatientsBuilder")
public class Patient extends User {
  @ManyToOne
  @JoinColumn(name = "user_doctor_id")
  private Doctor doctor;
  private String healthCompanyCode;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "patients_exemptions", joinColumns = @JoinColumn(name = "patient_id"), inverseJoinColumns = @JoinColumn(name = "exemption_id"))
  private List<Exemption> exemptions;
  @OneToMany(mappedBy = "patient")
  private Set<Appointment> appointments;
  @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
  private List<Prescription> prescriptions;

  public Patient(String name, String surname, LocalDate birthDate, String sex, String address,
                 Municipality municipality, String email, String password, String phoneNumber, UserRole role, Doctor doctor) {
    super(name, surname, birthDate, sex, address, municipality, email, password, phoneNumber, role);
    this.doctor = doctor;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Patient patient = (Patient) object;
    return Objects.equals(doctor, patient.doctor);
  }
  
}
