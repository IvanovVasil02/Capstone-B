package com.ivanovvasil.CapstoneB.prescription.payloads;

import com.ivanovvasil.CapstoneB.Medicine.MedicineDTO;
import com.ivanovvasil.CapstoneB.doctor.DoctorDTO;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientDTO;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import com.ivanovvasil.CapstoneB.prescription.enums.PriorityPrescription;
import com.ivanovvasil.CapstoneB.prescription.enums.TypeRecipe;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PrescriptionDTO(UUID id,
                              PatientDTO patient,
                              DoctorDTO doctor,
                              List<MedicineDTO> prescription,
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
