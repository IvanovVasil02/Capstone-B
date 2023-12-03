package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

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
  private UserRole role = UserRole.PATIENT;
}
