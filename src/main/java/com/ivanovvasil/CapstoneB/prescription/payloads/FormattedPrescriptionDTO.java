package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import lombok.Builder;

import java.util.Set;

@Builder
public record FormattedPrescriptionDTO(PatientResponseDTO patient,
                                       Set<PrescriptionDetails> prescription,
                                       int packagesNumber,
                                       String region,
                                       String provinceAbbr,
                                       String localHealthCode,
                                       Doctor doctor
) {
}
