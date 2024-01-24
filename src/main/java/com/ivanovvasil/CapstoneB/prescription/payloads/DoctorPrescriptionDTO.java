package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.validator.ValidEnum;

import java.util.List;

public record DoctorPrescriptionDTO(
        List<PrescriptionDetails> prescription,
        String diagnosticQuestion,
        @ValidEnum(enumClass = PriorityPrescription.class, message = "Invalid parameter", optional = true)
        String priority,
        @ValidEnum(enumClass = TypeRecipe.class, message = "Invalid parameter", optional = true)
        String typeRecipe
) {
}

