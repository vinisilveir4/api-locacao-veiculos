package com.vinisilveir4.locacao_veiculos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Table(name = "usuario")
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String nome;

    @Column
    String sobrenome;


    @Column
    String numero;

    @Column
    String endereco;

    @Column(nullable = true, length = 8)
    String cep;

    @Column(nullable = false, unique = true)
    String cpf;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permissoes_usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    Set<Permissao> permissoes;

    @CreationTimestamp
    @Column(updatable = false, name = "data_criacao")
    LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    LocalDateTime atualizadoEm;
}
