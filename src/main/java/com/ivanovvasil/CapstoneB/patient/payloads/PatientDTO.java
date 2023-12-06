package com.ivanovvasil.CapstoneB.patient.payloads;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record PatientDTO(
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
        @NotEmpty(message = "The municipality is required.")
        String municipality,

        @NotEmpty(message = "The email is required.")
        String email,
        @NotEmpty(message = "The password is required.")
        String password,

        @NotEmpty(message = "The doctor is required.")
        Doctor doctor) {
}
