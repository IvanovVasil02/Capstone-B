package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Prescription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @OneToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;
  @OneToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;
  @ManyToMany
  @JoinTable(
          name = "presciption_medicine",
          joinColumns = @JoinColumn(name = "prescription_id"),
          inverseJoinColumns = @JoinColumn(name = "medicine_id"))
  private List<Medicine> prescription;
  private int packagesNumber;
  private LocalDate isssuingDate;
  private String note;
  private String region;
  private String localHealthCode;
  private String diagnosticQuestion;
  private TypeRecipe typeRecipe;
  private PriorityPrescription priorityPrescription;
}
