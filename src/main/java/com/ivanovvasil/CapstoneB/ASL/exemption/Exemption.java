package com.ivanovvasil.CapstoneB.ASL.exemption;

import com.ivanovvasil.CapstoneB.patient.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Exemption {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String exemptionCode;
  @Column(name = "description", length = 1024)
  private String description;
  private String type;
  @ManyToMany(mappedBy = "exemptions")
  private List<Patient> exemptions;
}
