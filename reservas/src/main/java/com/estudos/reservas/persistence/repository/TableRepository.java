package com.estudos.reservas.persistence.repository;

import com.estudos.reservas.persistence.Table;
import com.estudos.reservas.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {

    Optional<Table> findByNumberTable(Integer numberTable);
}
