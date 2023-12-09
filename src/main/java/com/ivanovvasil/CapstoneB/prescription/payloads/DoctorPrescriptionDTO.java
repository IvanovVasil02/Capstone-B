package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.validator.ValidPriority;
import com.ivanovvasil.CapstoneB.prescription.validator.ValidTypeRecipe;

import java.util.Set;

public record DoctorPrescriptionDTO(
        Set<PrescriptionDetails> prescription,
        String note,
        String diagnosticQuestion,
        @ValidPriority(enumClass = PriorityPrescription.class, message = "Invalid parameter")
        String priority,
        @ValidTypeRecipe(enumClass = TypeRecipe.class, message = "Invalid parameter")
        String typeRecipe
) {
}

