package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.patient.Patient;
import lombok.Builder;

import java.util.List;

@Builder
public record FormattedPrescriptionDTO(Patient patient,
                                       List<Medicine> prescription,
                                       int packagesNumber,
                                       String region,
                                       String localHealthCode,
                                       String diagnosticQuestion,
                                       String typeRecipe) {
}
