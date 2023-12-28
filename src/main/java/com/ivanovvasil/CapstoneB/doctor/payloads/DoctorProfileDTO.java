package com.ivanovvasil.CapstoneB.doctor.payloads;

import com.ivanovvasil.CapstoneB.user.UserRole;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DoctorProfileDTO(
        UUID doctorId,
        String name,

        String surname,

        LocalDate birthDate,

        String sex,

        String fiscalCode,

        String address,
        String municipality,

        String email,
        String phoneNumber,
        UserRole role) {
}
