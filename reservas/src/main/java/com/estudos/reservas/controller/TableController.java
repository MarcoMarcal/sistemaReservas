package com.estudos.reservas.controller;

import com.estudos.reservas.persistence.Table;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {

    @GetMapping()
    public List<Table> tablesList() {
        return null;
    }
}
