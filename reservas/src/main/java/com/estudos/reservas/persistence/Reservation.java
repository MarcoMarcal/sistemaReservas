package com.estudos.reservas.persistence;

import com.estudos.reservas.enums.ReservationStatus;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@Table(name = "tb_reservations", schema = "public")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "table_id", nullable = false)
    private Integer tableId;
    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    
    private Reservation() {}
    public Long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }
    public static class Builder {
        private final Reservation reservation;

        public Builder() {
            reservation = new Reservation();
        }

        public Reservation.Builder id(Long id) {
            reservation.id = id;
            return this;
        }

        public Reservation.Builder userId(Integer userId) {
            reservation.userId = userId;
            return this;
        }

        public Reservation.Builder tableId(Integer tableId) {
            reservation.tableId = tableId;
            return this;
        }

        public Reservation.Builder reservationDate(Date reservationDate) {
            reservation.reservationDate = reservationDate;
            return this;
        }

        public Reservation.Builder status(ReservationStatus status) {
            reservation.status = status;
            return this;
        }

        public Reservation build() {
            return reservation;
        }
    }
}
