package com.ivanovvasil.CapstoneB.patient.payloads;

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
        String doctor,
        List<String> exemptions) {
}
