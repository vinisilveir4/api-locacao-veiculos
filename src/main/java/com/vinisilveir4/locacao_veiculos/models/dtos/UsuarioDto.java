package com.vinisilveir4.locacao_veiculos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioDto {

    Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 100)
    String sobrenome;

    String numero;

    @NotBlank(message = "Endereço é obrigatório")
    String endereco;

    String cep;

    @CPF(message = "Cpf inválido")
    String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100)
    String email;

    @Size(min = 8, max = 50, message = "A senha deve ter no mínimo 8 caracteres")
    String senha;

    Set<String> permissoes;
}
