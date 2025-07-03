package com.estudos.reservas.model.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        Integer capacity,
        LocalDate date,
        LocalTime time
) {
}
