package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record MedicinePrescriptionDTO(
        @NotEmpty(message = "Empty prescription!")
        List<PrescriptionDetails> prescription) {
}
