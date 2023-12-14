package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;

import java.util.Set;

public record PatientPrescriptionDTO(
        Set<PrescriptionDetails> prescription) {
}
