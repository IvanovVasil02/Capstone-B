package com.ivanovvasil.CapstoneB.doctor;

public record DoctorUpdateDTO(
        String phoneNumber,
        String email,
        String emergencyContact,
        String province) {
}
