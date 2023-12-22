package com.ivanovvasil.CapstoneB.patient.payloads;

import com.ivanovvasil.CapstoneB.doctor.DoctorProfileDTO;
import com.ivanovvasil.CapstoneB.user.UserRole;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PatientResponseDTO(
        String name,
        String surname,
        LocalDate birthDate,
        String sex,
        String fiscalCode,
        String address,
        String municipality,
        String email,
        String phoneNumber,
        DoctorProfileDTO doctor,
        List<String> exemptions,
        UserRole role
) {
}
