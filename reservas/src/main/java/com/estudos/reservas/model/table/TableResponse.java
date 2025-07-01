package com.estudos.reservas.model.table;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TableResponse(
        Integer numberTable,
        String status,
        LocalDateTime timeStamp
) {
}
