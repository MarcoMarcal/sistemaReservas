package com.estudos.reservas.controller;

import com.estudos.reservas.model.table.TableRequest;
import com.estudos.reservas.persistence.repository.TableRepository;
import com.estudos.reservas.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;
    private final TableRepository tableRepository;

    @GetMapping()
    public ResponseEntity<?> tableList() {
        return ResponseEntity.ok(tableRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> addTable(@RequestBody TableRequest tableRequest) {
        var response = tableService.createTable(tableRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{tableNumber}")
    public ResponseEntity<?> deleteTable(@PathVariable Integer tableNumber) {
        tableService.deleteTable(tableNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{numberTable}")
    public ResponseEntity<?> updateTable(@PathVariable Integer tableNumber, @RequestBody TableRequest tableRequest) {
        tableService.updateTable(tableNumber, tableRequest);
        return ResponseEntity.noContent().build();
    }
}