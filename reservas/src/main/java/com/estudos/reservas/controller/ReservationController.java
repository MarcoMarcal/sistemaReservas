package com.estudos.reservas.controller;

import com.estudos.reservas.model.reservation.ReservationRequest;
import com.estudos.reservas.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping()
    public ResponseEntity<?> createReserve(@RequestBody ReservationRequest request) {
        var response = reservationService.createReserve(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<?> getReserve() {
        return null;
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelReserve() {
        return null;
    }
}
