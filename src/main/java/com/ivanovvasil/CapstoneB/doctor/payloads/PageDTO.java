package com.ivanovvasil.CapstoneB.doctor.payloads;

import org.springframework.data.domain.Page;

public record PageDTO(Page<?> page, Long pending) {
}
