package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.MedicineDTO;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record FormattedPrescriptionDTO(PatientResponseDTO patient,
                                       List<MedicineDTO> prescription,
                                       int packagesNumber,
                                       String region,
                                       String localHealthCode,
                                       Doctor doctor
) {
}
