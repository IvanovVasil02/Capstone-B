package com.ivanovvasil.CapstoneB.prescription.payloads;

import java.util.List;

public record PatientPrescriptionDTO(
        List<String> prescription,
        Integer packagesNumber) {
}
