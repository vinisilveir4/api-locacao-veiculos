package com.vinisilveir4.locacao_veiculos.dtos;

import com.vinisilveir4.locacao_veiculos.util.TipoStatusLocacao;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocacaoDto {

    private Long id;
    private Long usuarioId;
    private Long veiculoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private BigDecimal valor;
    private TipoStatusLocacao status;
    private LocalDateTime dataCriacao;

}
