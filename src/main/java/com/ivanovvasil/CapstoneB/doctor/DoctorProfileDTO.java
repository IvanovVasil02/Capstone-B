package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.prescription.validator.ValidMunicipality;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DoctorProfileDTO(
        UUID doctorId,
        @NotEmpty(message = "The name is required.")
        String name,

        @NotEmpty(message = "The surname is required.")
        String surname,

        @NotEmpty(message = "The birthDate is required.")
        LocalDate birthDate,

        @NotEmpty(message = "The sex is required.")
        String sex,

        @NotEmpty(message = "The fiscalCode is required.")
        String fiscalCode,

        @NotEmpty(message = "The address is required.")
        String address,
        @ValidMunicipality(message = "The municipality is required.")
        String municipality,

        @NotEmpty(message = "The email is required.")
        String email,
        @NotEmpty(message = "The password is required.")
        String password,
        @NotEmpty(message = "The phone number is required.")
        String phoneNumber) {
}
