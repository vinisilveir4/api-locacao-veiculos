package com.vinisilveir4.locacao_veiculos.repositories;

import com.vinisilveir4.locacao_veiculos.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Optional<Veiculo> findVeiculoById(Long id);
}
