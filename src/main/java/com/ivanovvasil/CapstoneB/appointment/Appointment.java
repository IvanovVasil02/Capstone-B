package com.ivanovvasil.CapstoneB.appointment;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private LocalDate date;
  private LocalTime time;
  @ManyToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;
  @ManyToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;
  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;
}
