package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;

import java.time.LocalDate;
import java.util.List;

public record FormattedPrescriptionDTO(Patient patient,
                                       Doctor doctor,
                                       List<Medicine> prescription,
                                       int packagesNumber,
                                       LocalDate isssuingDate,
                                       String note,
                                       String region,
                                       String localHealthCode,
                                       String diagnosticQuestion,
                                       String typeRecipe) {
}
