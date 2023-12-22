package com.ivanovvasil.CapstoneB.appointment.payloads;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record FixAppointmentDTO(
        UUID id,
        LocalDate date,
        LocalTime time

) {
}
