package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.doctor.payloads.DoctorDTO;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionVerificationStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record PrescriptionDTO(
        UUID prescriptionID,
        PatientResponseDTO patient,
        DoctorDTO doctor,
        Set<PrescriptionDetailsDTO> prescription,
        int packagesNumber,
        LocalDateTime issuingDate,
        String region,
        String provinceAbbr,
        String localHealthCode,
        String diagnosticQuestion,
        TypeRecipe typeRecipe,
        PriorityPrescription priorityPrescription,
        PrescriptionStatus status,
        PrescriptionVerificationStatus verificationStatus) {
}
