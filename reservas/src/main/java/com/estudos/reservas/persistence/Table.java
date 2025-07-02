package com.estudos.reservas.persistence;

import com.estudos.reservas.enums.TableStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@AllArgsConstructor
@Builder
@Data
@jakarta.persistence.Table(name = "tb_tables", schema = "public")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_table", nullable = false)
    private Integer numberTable;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TableStatus status;
}
