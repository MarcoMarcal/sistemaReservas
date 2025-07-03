package com.estudos.reservas.persistence;

import com.estudos.reservas.enums.ReservationStatus;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "tb_reservations", schema = "public")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "table_id", nullable = false)
    private Long tableId;
    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
