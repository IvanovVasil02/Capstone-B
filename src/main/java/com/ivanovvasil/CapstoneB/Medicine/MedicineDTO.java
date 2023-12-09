package com.ivanovvasil.CapstoneB.Medicine;

import java.util.UUID;

public record MedicineDTO(
        UUID medicineId,
        String activeIngredient,
        String nameAndPackaginf,
        String publicPrice) {
}
