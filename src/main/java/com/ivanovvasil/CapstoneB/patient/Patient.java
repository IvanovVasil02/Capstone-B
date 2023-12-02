package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  private String surname;
  private LocalDate birthDate;
  private String sex;
  private String fiscalCode;
  private String address;
  private String email;
  private String password;
  @OneToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;
}
