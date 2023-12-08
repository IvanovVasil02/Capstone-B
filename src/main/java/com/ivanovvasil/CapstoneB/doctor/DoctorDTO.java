package com.ivanovvasil.CapstoneB.doctor;

import jakarta.validation.constraints.NotEmpty;

public record DoctorDTO(@NotEmpty(message = "The name is required.")
                        String name,
                        @NotEmpty(message = "The surname is required.")
                        String surname,
                        @NotEmpty(message = "The fiscalCode is required.")
                        String fiscalCode,
                        String doctorId) {
}
