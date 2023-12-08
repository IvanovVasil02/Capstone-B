package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.doctor.DoctorDTO;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record PrescriptionDTO(
        PatientResponseDTO patient,
        DoctorDTO doctor,
        Set<PrescriptionDetailsDTO> prescription,
        int packagesNumber,
        LocalDate isssuingDate,
        String note,
        String region,
        String localHealthCode,
        String diagnosticQuestion,
        TypeRecipe typeRecipe,
        PriorityPrescription priorityPrescription,
        PrescriptionStatus status) {
}
