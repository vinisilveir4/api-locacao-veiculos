package com.vinisilveir4.locacao_veiculos.config.exceptionHandler;

import com.vinisilveir4.locacao_veiculos.models.dtos.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> genericException(Exception ex) {
//        ApiError apiError = new ApiError(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                List.of(ex.getMessage()),
//                LocalDateTime.now());
//
//        return ResponseEntity.internalServerError().body(apiError);
//    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ApiError> notFoundException(RuntimeException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                List.of(ex.getMessage()),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(VeiculoNaoDisponivel.class)
    public ResponseEntity<ApiError> vNaoDisponivel(RuntimeException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.EXPECTATION_FAILED,
                List.of(ex.getMessage()),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(apiError);
    }

    @ExceptionHandler(ConflitoDeDado.class)
    public ResponseEntity<ApiError> dadosRepetidos(ConflitoDeDado ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT,
                List.of(ex.getMessage()),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> argumentNotValidException(MethodArgumentNotValidException ex) {
//        List<String> erros = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        ApiError apiError = new ApiError(
//                HttpStatus.BAD_REQUEST,
//                erros,
//                LocalDateTime.now());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
//    }

    @ExceptionHandler(ErroLogin.class)
    public ResponseEntity<ApiError> erroLogin(ErroLogin ex) {
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                List.of(ex.getMessage()),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
