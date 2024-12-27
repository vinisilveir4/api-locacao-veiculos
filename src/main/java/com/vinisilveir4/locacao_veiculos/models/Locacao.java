package com.vinisilveir4.locacao_veiculos.models;

import com.vinisilveir4.locacao_veiculos.util.TipoStatusLocacao;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "locacao")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    Veiculo veiculo;

    @Column(nullable = false, name = "data_inicio")
    LocalDateTime dataInicio;

    @Column(nullable = false, name = "data_fim")
    LocalDateTime dataFim;

    @Column(nullable = false)
    BigDecimal valor;

    @Column(name = "status_", nullable = false)
    @Enumerated(EnumType.STRING)
    TipoStatusLocacao status;

    @CreationTimestamp
    @Column(updatable = false, name = "data_criacao")
    LocalDateTime dataCriacao;
}
