package com.ivanovvasil.CapstoneB.exceptions.ExceptionPayloads;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}
