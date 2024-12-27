package com.vinisilveir4.locacao_veiculos.models.dtos;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    HttpStatus code;
    List<String> erros;
    LocalDateTime timestamp;

    public ApiError(HttpStatus code, List<String> erros, LocalDateTime timestamp) {
        this.code = code;
        this.erros = erros;
        this.timestamp = timestamp;
    }
}
