package com.vinisilveir4.locacao_veiculos.dtos;

import com.vinisilveir4.locacao_veiculos.util.TipoStatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class VeiculoDto {

    Long id;

    @NotBlank(message = "O modelo é obrigatório")
    String modelo;

    @NotBlank(message = "A marca é obrigatório")
    String marca;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate anoFabricacao;

    @NotBlank(message = "Placa é obrigatório")
    @Length(min = 7, max = 7)
    String placa;

    TipoStatusVeiculo status;

    @DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que 0.0")
    BigDecimal valorDiario;
}
