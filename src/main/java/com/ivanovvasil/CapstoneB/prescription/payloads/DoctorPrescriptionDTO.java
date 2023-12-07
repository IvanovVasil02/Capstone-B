package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import com.ivanovvasil.CapstoneB.prescription.validator.ValidPriority;
import com.ivanovvasil.CapstoneB.prescription.validator.ValidTypeRecipe;

import java.util.List;

public record DoctorPrescriptionDTO(
        List<Medicine> prescription,
        int packagesNumber,
        String note,
        String diagnosticQuestion,
        @ValidPriority(enumClass = PriorityPrescription.class, message = "Invalid parameter")
        String priority,
        @ValidTypeRecipe(enumClass = TypeRecipe.class, message = "Invalid parameter")
        String typeRecipe
) {
}

