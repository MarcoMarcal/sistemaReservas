package com.estudos.reservas.model.table;

import com.estudos.reservas.enums.TableStatus;
import jakarta.annotation.Nullable;

public record TableRequest(
        Integer numberTable,
        Integer capacity,
        @Nullable
        TableStatus tableStatus
) {
}
