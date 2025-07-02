package com.estudos.reservas.service;

import com.estudos.reservas.enums.TableStatus;
import com.estudos.reservas.exception.TableAlreadyRegisterException;
import com.estudos.reservas.model.table.CreateTableRequest;
import com.estudos.reservas.persistence.Table;
import com.estudos.reservas.persistence.repository.TableRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {
    @InjectMocks
    private TableService service;
    @Mock
    private TableRepository tableRepository;

    @Test
    void createTable_success() {
       var request = new CreateTableRequest(1,5);
       var response = service.createTable(request);

        Mockito.verify(tableRepository, times(1)).save(any());
        assertEquals(1, response.numberTable());
    }

    @Test
    void createTable_throwsException() {
        var request = new CreateTableRequest(1,5);
        var table = Table.builder()
                .numberTable(1)
                .status(TableStatus.DISPONIVEL)
                .id(1L)
                .capacity(5)
                .build();
        doReturn(Optional.of(table)).when(tableRepository).findByNumberTable(request.numberTable());

        assertThrows(TableAlreadyRegisterException.class, () -> service.createTable(request));
        Mockito.verify(tableRepository, never()).save(any());
    }
}