package com.ivanovvasil.CapstoneB.doctor.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DoctorDTO(@NotEmpty(message = "The name is required.")
                        String name,
                        @NotEmpty(message = "The surname is required.")
                        String surname,
                        @NotEmpty(message = "The fiscalCode is required.")
                        String fiscalCode,
                        UUID doctorId) {
}
