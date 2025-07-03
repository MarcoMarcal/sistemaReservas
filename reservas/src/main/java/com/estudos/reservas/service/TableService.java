package com.estudos.reservas.service;

import com.estudos.reservas.enums.TableStatus;
import com.estudos.reservas.exception.TableAlreadyRegisterException;
import com.estudos.reservas.exception.TableNotFoundException;
import com.estudos.reservas.model.table.TableRequest;
import com.estudos.reservas.model.table.TableResponse;
import com.estudos.reservas.persistence.Table;
import com.estudos.reservas.persistence.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public TableResponse createTable(TableRequest tableRequest) {
        tableRepository.findByNumberTable(tableRequest.numberTable())
                .ifPresent(table -> {
                    throw new TableAlreadyRegisterException("Table Already Register");
                });

        saveTable(tableRequest);

        return TableResponse.builder()
                .numberTable(tableRequest.numberTable())
                .timeStamp(LocalDateTime.now())
                .status("Table Registered")
                .build();
    }

    public void deleteTable(Integer tableNumber) {
       tableRepository.findByNumberTable(tableNumber)
            .ifPresentOrElse(tableRepository::delete,
                    () -> {throw new TableNotFoundException("Table not found");}
            );
    }

    public void updateTable(Integer tableNumber, TableRequest tableRequest) {
        var table = tableRepository.findByNumberTable(tableNumber)
                .orElseThrow(() -> new TableNotFoundException("Table not found"));

        if (tableRequest.numberTable() != null) {
            table.setNumberTable(tableRequest.numberTable());
        }
        if (tableRequest.tableStatus() != null) {
            table.setStatus(tableRequest.tableStatus());
        }
        if (tableRequest.capacity() != null) {
            table.setCapacity(tableRequest.capacity());
        }

        tableRepository.save(table);
    }


    private void saveTable(TableRequest tableRequest) {
        var table = Table.builder()
                .numberTable(tableRequest.numberTable())
                .status(TableStatus.DISPONIVEL)
                .capacity(tableRequest.capacity())
                .build();

        tableRepository.save(table);
    }
}
