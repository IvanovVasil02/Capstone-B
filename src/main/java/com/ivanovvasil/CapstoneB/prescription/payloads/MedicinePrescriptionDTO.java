package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record MedicinePrescriptionDTO(
        @NotEmpty(message = "Empty prescription!")
        Set<PrescriptionDetails> prescription) {
}
