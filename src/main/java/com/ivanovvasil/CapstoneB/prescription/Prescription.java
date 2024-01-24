package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionVerificationStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
  @OneToMany(mappedBy = "prescription", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<PrescriptionDetails> prescription = new ArrayList<>();
  private int packagesNumber;
  private LocalDateTime issuingDate;
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
  @Enumerated(EnumType.STRING)
  private PrescriptionVerificationStatus verificationStatus;

  @Override
  public String toString() {
    StringBuilder detailsString = new StringBuilder();

    if (prescription != null) {
      for (PrescriptionDetails details : prescription) {
        detailsString.append(details.toString()).append(", ");
      }
    }

    return "Prescription{" +
            "prescription= [" + detailsString + "]" +
            " id=" + id +
            '}';
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Prescription that = (Prescription) object;
    return packagesNumber == that.packagesNumber
            && Objects.equals(patient, that.patient)
            && Objects.equals(doctor, that.doctor)
            && Objects.equals(region, that.region)
            && Objects.equals(provinceAbbr, that.provinceAbbr)
            && Objects.equals(localHealthCode, that.localHealthCode)
            && prescription.stream().map(PrescriptionDetails::getMedicine).map(Medicine::getId)
            .collect(Collectors.toSet())
            .equals(that.prescription.stream().map(PrescriptionDetails::getMedicine).map(Medicine::getId)
                    .collect(Collectors.toSet()));


  }
}
