package com.vinisilveir4.locacao_veiculos.repositories;

import com.vinisilveir4.locacao_veiculos.models.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    Optional<Permissao> findPermissaoByNome(String nome);
}
