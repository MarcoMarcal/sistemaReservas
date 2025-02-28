package com.estudos.reservas.persistence;

import com.estudos.reservas.enums.TableStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
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

    private Table() {
    }
    public Long getId() {
        return id;
    }

    public Integer getNumberTable() {
        return numberTable;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public static class Builder {
        private final Table table;

        public Builder() {
            table = new Table();
        }

        public Table.Builder id(Long id) {
            table.id = id;
            return this;
        }

        public Table.Builder numberTable(Integer numberTable) {
            table.numberTable = numberTable;
            return this;
        }

        public Table.Builder capacity(Integer capacity) {
            table.capacity = capacity;
            return this;
        }

        public Table.Builder status(TableStatus status) {
            table.status = status;
            return this;
        }

        public Table build() {
            return table;
        }
    }
}
