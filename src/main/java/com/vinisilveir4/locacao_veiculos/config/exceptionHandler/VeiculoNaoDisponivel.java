package com.vinisilveir4.locacao_veiculos.config.exceptionHandler;

public class VeiculoNaoDisponivel extends RuntimeException{
    public VeiculoNaoDisponivel() {
        super("Veículo não disponivel");
    }
}
