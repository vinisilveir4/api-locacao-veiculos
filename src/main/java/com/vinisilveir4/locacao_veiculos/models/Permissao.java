package com.vinisilveir4.locacao_veiculos.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Table(name = "permissao")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Permissao {
//    enum TipoPermissao {ADMIN, USUARIO, VENDEDOR}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    String nome;
}
