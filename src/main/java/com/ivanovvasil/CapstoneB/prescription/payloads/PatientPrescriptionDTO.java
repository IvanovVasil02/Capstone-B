package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;

import java.util.List;

public record PatientPrescriptionDTO(
        List<Medicine> prescription,
        int packagesNumber) {
}
