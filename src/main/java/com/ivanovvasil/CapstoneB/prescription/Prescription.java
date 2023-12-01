package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Prescription {
  private UUID id;
  private Patient patient;
  private Doctor doctor;
  private List<Medicine> prescription;
  private int packagesNumber;
  private LocalDate isssuingDate;
  private String note;
  private String region;
  private String localHealthCode;
  private String diagnosticQuestion;
  private String typeRecipe;
}
