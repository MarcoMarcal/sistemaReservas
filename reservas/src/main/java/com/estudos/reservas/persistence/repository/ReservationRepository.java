package com.estudos.reservas.persistence.repository;

import com.estudos.reservas.persistence.Reservation;
import com.estudos.reservas.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
