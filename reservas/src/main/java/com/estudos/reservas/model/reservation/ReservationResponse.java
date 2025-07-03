package com.estudos.reservas.model.reservation;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResponse(
        Integer numberTable,
        String description,
        LocalDateTime reservationDate
) {
}
