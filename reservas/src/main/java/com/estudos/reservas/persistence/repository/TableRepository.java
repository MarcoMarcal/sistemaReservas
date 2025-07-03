package com.estudos.reservas.persistence.repository;

import com.estudos.reservas.persistence.Table;
import com.estudos.reservas.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {

    Optional<Table> findByNumberTable(Integer numberTable);

    @Query("""
        SELECT t FROM Table t
        WHERE t.capacity >= :capacity
        AND NOT EXISTS (
            SELECT r FROM Reservation r
            WHERE r.tableId = t.id
            AND r.reservationDate = :reservationDate
        )
    """)
    List<Table> findAvailableTablesByCapacityAndData(@Param("capacity") Integer capacity, @Param("reservationDate") LocalDateTime reservationDate);
}
