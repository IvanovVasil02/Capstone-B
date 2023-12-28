package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.doctor.payloads.DoctorDTO;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
public record PrescriptionDTO(
        UUID prescriptionID,
        PatientResponseDTO patient,
        DoctorDTO doctor,
        Set<PrescriptionDetailsDTO> prescription,
        int packagesNumber,
        LocalDate isssuingDate,
        String note,
        String region,
        String provinceAbbr,
        String localHealthCode,
        String diagnosticQuestion,
        TypeRecipe typeRecipe,
        PriorityPrescription priorityPrescription,
        PrescriptionStatus status) {
}
