package com.vinisilveir4.locacao_veiculos.config.exceptionHandler;

public class NotFound extends RuntimeException {
    public NotFound(String mensagem) {
        super(mensagem);
    }
}
