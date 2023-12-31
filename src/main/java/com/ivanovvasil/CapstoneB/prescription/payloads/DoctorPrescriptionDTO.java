package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.validator.ValidEnum;

import java.util.Set;

public record DoctorPrescriptionDTO(
        Set<PrescriptionDetails> prescription,
        String diagnosticQuestion,
        @ValidEnum(enumClass = PriorityPrescription.class, message = "Invalid parameter")
        String priority,
        @ValidEnum(enumClass = TypeRecipe.class, message = "Invalid parameter")
        String typeRecipe
) {
}

