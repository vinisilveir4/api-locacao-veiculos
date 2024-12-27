package com.vinisilveir4.locacao_veiculos.repositories;

import com.vinisilveir4.locacao_veiculos.models.Locacao;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    List<Locacao> findLocacaoByStatus(TipoStatusLocacao statusLocacao);
}
