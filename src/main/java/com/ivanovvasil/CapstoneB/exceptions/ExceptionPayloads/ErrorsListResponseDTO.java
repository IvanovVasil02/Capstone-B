package com.ivanovvasil.CapstoneB.exceptions.ExceptionPayloads;

import java.util.Date;
import java.util.List;

public record ErrorsListResponseDTO(String message,
                                    Date timeStamp,
                                    List<String> errorsList) {
}
