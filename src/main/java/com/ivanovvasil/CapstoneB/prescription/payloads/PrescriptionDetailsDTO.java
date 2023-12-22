package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.MedicineDTO;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PrescriptionDetailsDTO(
        UUID id,
        MedicineDTO medicine,
        int quantity) {
}
