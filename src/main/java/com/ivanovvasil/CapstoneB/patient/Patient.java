package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.ASL.exemption.Exemption;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.user.User;
import jakarta.persistence.*;
import lombok.*;

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
  @ManyToMany
  @JoinTable(
          name = "patients_exemptions",
          joinColumns = @JoinColumn(name = "patient_id"),
          inverseJoinColumns = @JoinColumn(name = "exemption_id"))
  private List<Exemption> exemptions;
}
