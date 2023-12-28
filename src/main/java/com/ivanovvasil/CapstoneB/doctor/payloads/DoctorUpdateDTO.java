package com.ivanovvasil.CapstoneB.doctor.payloads;

public record DoctorUpdateDTO(
        String phoneNumber,
        String email,
        String emergencyContact,
        String province) {
}
