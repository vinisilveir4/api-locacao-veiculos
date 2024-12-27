package com.vinisilveir4.locacao_veiculos.models;


import com.vinisilveir4.locacao_veiculos.util.TipoStatusVeiculo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Table(name = "veiculo")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String modelo;

    @Column(nullable = false)
    String marca;

    @Column(name = "ano_fabricacao", nullable = false)
    LocalDate anoFabricacao;

    @Column(nullable = false, length = 8, unique = true)
    String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status_")
    TipoStatusVeiculo status;

    @Column(nullable = false, name = "valor_diario")
    BigDecimal valorDiario;
}
