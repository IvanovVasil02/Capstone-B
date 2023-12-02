package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;

import java.util.List;
import java.util.UUID;

public record DoctorPrescriptionDTO(
        UUID id,
        List<Medicine> prescription,
        int packagesNumber,
        String note,
        String diagnosticQuestion,
        String typeRecipe
) {
}

