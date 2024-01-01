package com.ivanovvasil.CapstoneB.appointment.payloads;

import com.ivanovvasil.CapstoneB.doctor.payloads.DoctorDTO;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record AppointmentDTO(
        UUID id,
        LocalDate date,
        LocalTime time,
        PatientResponseDTO patient,
        DoctorDTO doctor,
        String status) {
}
