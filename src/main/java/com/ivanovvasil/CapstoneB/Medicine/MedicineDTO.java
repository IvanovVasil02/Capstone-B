package com.ivanovvasil.CapstoneB.Medicine;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MedicineDTO(
        UUID medicineId,
        String activeIngredient,
        String nameAndPackaging,
        String holderOfMarketingAuthorization,
        String identificationCode,
        String publicPrice) {

}
