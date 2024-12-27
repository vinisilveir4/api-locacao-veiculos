package com.vinisilveir4.locacao_veiculos.services;

import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.NotFound;
import com.vinisilveir4.locacao_veiculos.dtos.VeiculoDto;
import com.vinisilveir4.locacao_veiculos.models.Veiculo;
import com.vinisilveir4.locacao_veiculos.repositories.VeiculoRepository;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusVeiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public String cadastrar(VeiculoDto veiculoDto) {

        final var veiculo = new Veiculo();
        veiculo.setAnoFabricacao(veiculoDto.getAnoFabricacao());
        veiculo.setMarca(veiculoDto.getMarca());
        veiculo.setValorDiario(veiculoDto.getValorDiario());
        veiculo.setModelo(veiculoDto.getModelo());
        veiculo.setPlaca(veiculoDto.getPlaca());
        veiculo.setStatus(TipoStatusVeiculo.DISPONIVEL);

        Veiculo veiculoCriado = veiculoRepository.save(veiculo);

        return String.format("Id: %d", veiculoCriado.getId());
    }

    public void remover(Long id) throws RuntimeException {
        veiculoRepository.findVeiculoById(id)
                .orElseThrow(() -> new NotFound("Veículo não encontrado"));

        veiculoRepository.deleteById(id);
    }

    public void editarStatus(Long id, TipoStatusVeiculo novoStatus)  {
        var veiculo = veiculoRepository.findVeiculoById(id)
                .orElseThrow(() -> new NotFound("Veículo não encontrado"));
        veiculo.setStatus(novoStatus);

        veiculoRepository.save(veiculo);
    }

    public List<VeiculoDto> veiculosDisponiveis() {
        return buscarEConverter()
                .stream()
                .filter(item -> item.getStatus() == TipoStatusVeiculo.DISPONIVEL)
                .collect(Collectors.toList());
    }

    public List<VeiculoDto> todosVeiculos() { return buscarEConverter();}

    private List<VeiculoDto> buscarEConverter() {
        return veiculoRepository
                .findAll()
                .stream()
                .map(item -> {
                    var veiculosDto = new VeiculoDto();
                    veiculosDto.setId(item.getId());
                    veiculosDto.setStatus(item.getStatus());
                    veiculosDto.setPlaca(item.getPlaca());
                    veiculosDto.setModelo(item.getModelo());
                    veiculosDto.setMarca(item.getMarca());
                    veiculosDto.setValorDiario(item.getValorDiario());
                    veiculosDto.setAnoFabricacao(item.getAnoFabricacao());

                    return veiculosDto;
                })
                .collect(Collectors.toList());
    }

    public Optional<Veiculo> buscarVeiculo(Long id) {
        return veiculoRepository.findById(id);
    }
}
