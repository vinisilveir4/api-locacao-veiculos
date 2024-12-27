package com.vinisilveir4.locacao_veiculos.config.exceptionHandler;

public class ConflitoDeDado extends RuntimeException {
    public ConflitoDeDado(String mensagem) {
        super(mensagem);
    }
}
