package com.ivanovvasil.CapstoneB.patient.payloads;

import com.ivanovvasil.CapstoneB.doctor.payloads.DoctorProfileDTO;
import com.ivanovvasil.CapstoneB.user.UserRole;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record PatientResponseDTO(
        UUID patientId,
        String name,
        String surname,
        LocalDate birthDate,
        String sex,
        String fiscalCode,
        String address,
        String municipalityDenomination,
        String municipality,
        String email,
        String phoneNumber,
        DoctorProfileDTO doctor,
        List<String> exemptions,
        UserRole role
) {
}
