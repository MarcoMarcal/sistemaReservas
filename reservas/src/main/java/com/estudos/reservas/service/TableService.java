package com.estudos.reservas.service;

import com.estudos.reservas.enums.TableStatus;
import com.estudos.reservas.enums.UserType;
import com.estudos.reservas.exception.TableAlreadyRegisterException;
import com.estudos.reservas.exception.UserRegisterException;
import com.estudos.reservas.model.authentication.AuthResponse;
import com.estudos.reservas.model.authentication.UserLoginRequest;
import com.estudos.reservas.model.authentication.UserRegisterRequest;
import com.estudos.reservas.model.table.CreateTableRequest;
import com.estudos.reservas.model.table.TableResponse;
import com.estudos.reservas.persistence.Table;
import com.estudos.reservas.persistence.User;
import com.estudos.reservas.persistence.repository.TableRepository;
import com.estudos.reservas.persistence.repository.UserRepository;
import com.estudos.reservas.util.JWTTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public TableResponse createTable(CreateTableRequest createTableRequest) {
        tableRepository.findByNumberTable(createTableRequest.numberTable())
                .ifPresent(table -> {
                    throw new TableAlreadyRegisterException("Table Already Register");
                });

        saveTable(createTableRequest);

        return TableResponse.builder()
                .numberTable(createTableRequest.numberTable())
                .timeStamp(LocalDateTime.now())
                .status("Table Registered")
                .build();
    }

    private void saveTable(CreateTableRequest createTableRequest) {
        var table = Table.builder()
                .numberTable(createTableRequest.numberTable())
                .status(TableStatus.DISPONIVEL)
                .capacity(createTableRequest.capacity())
                .build();

        tableRepository.save(table);
    }
}
