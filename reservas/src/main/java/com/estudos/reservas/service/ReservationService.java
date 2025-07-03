package com.estudos.reservas.service;

import com.estudos.reservas.configuration.CustomUserDetails;
import com.estudos.reservas.enums.ReservationStatus;
import com.estudos.reservas.exception.NotPossibleReserveException;
import com.estudos.reservas.model.reservation.ReservationRequest;
import com.estudos.reservas.model.reservation.ReservationResponse;
import com.estudos.reservas.persistence.Reservation;
import com.estudos.reservas.persistence.repository.ReservationRepository;
import com.estudos.reservas.persistence.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    public ReservationResponse createReserve(ReservationRequest reservationRequest) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (CustomUserDetails) auth.getPrincipal();
        var userId = userDetails.getId();

        var reserveDateTime = reservationRequest.date().atTime(reservationRequest.time());
        var availableTables = tableRepository.findAvailableTablesByCapacityAndData(reservationRequest.capacity(), reserveDateTime);

        if (availableTables.isEmpty()) {
            throw new NotPossibleReserveException("Don't have table for make reserve");
        }

        var table = availableTables.get(0);
        var reserve = Reservation.builder()
                        .reservationDate(reserveDateTime)
                        .tableId(table.getId())
                        .status(ReservationStatus.ATIVO)
                        .userId(userId)
                        .build();

        reservationRepository.save(reserve);

        return ReservationResponse.builder()
                .numberTable(table.getNumberTable())
                .description("Reserved table")
                .reservationDate(reserveDateTime)
                .build();
    }
}
