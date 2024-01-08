package com.ivanovvasil.CapstoneB.patient.payloads;

import com.ivanovvasil.CapstoneB.prescription.validator.ValidMunicipality;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientDTO(
        @NotEmpty(message = "The name is required.")
        String name,

        @NotEmpty(message = "The surname is required.")
        String surname,

        @NotNull(message = "The birthDate is required.")
        LocalDate birthDate,

        @NotEmpty(message = "The sex is required.")
        String sex,

        @NotEmpty(message = "The address is required.")
        String address,
        @ValidMunicipality(message = "The municipality is required or not valid.")
        String postalCode,

        @NotEmpty(message = "The email is required.")
        String email,
        @NotEmpty(message = "The password is required.")
        String password,
        @NotEmpty(message = "The phone number is required.")
        String phoneNumber,
        String doctorId) {
}
