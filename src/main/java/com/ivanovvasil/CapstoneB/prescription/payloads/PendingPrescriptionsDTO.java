package com.ivanovvasil.CapstoneB.prescription.payloads;

import org.springframework.data.domain.Page;

public record PendingPrescriptionsDTO(Page<PrescriptionDTO> noVerifiedPrescriptions,
                                      Page<PrescriptionDTO> verifiedPrescriptions) {
}
