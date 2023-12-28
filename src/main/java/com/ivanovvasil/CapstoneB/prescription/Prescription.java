package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "prescriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Prescription {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "patient_id")
  private Patient patient;
  @ManyToOne
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;
  @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PrescriptionDetails> prescription = new HashSet<>();
  private int packagesNumber;
  private LocalDate issuingDate;
  private String note;
  private String region;
  private String provinceAbbr;
  private String localHealthCode;
  private String diagnosticQuestion;
  @Enumerated(EnumType.STRING)
  private TypeRecipe typeRecipe;
  @Enumerated(EnumType.STRING)
  private PriorityPrescription priorityPrescription;
  @Enumerated(EnumType.STRING)
  private PrescriptionStatus status;
}
