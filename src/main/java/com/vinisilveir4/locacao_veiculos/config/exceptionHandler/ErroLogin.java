package com.vinisilveir4.locacao_veiculos.config.exceptionHandler;

public class ErroLogin extends RuntimeException {
    public ErroLogin(String mensagem) {
        super(mensagem);
    }
}
