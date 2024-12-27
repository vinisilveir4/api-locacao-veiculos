package com.vinisilveir4.locacao_veiculos.services;

import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.NotFound;
import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.VeiculoNaoDisponivel;
import com.vinisilveir4.locacao_veiculos.dtos.LocacaoDto;
import com.vinisilveir4.locacao_veiculos.models.Locacao;
import com.vinisilveir4.locacao_veiculos.repositories.LocacaoRepository;
import com.vinisilveir4.locacao_veiculos.repositories.UsuarioRepository;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusVeiculo;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusLocacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeiculoService veiculoService;

    public LocacaoDto locarVeiculo(LocacaoDto locacaoDto) {
        final var usuario = usuarioRepository.findById(locacaoDto.getUsuarioId())
                    .orElseThrow(() -> new NotFound("Usuário não encontrado"));

        final var veiculo = veiculoService.buscarVeiculo(locacaoDto.getVeiculoId())
                .orElseThrow(() -> new NotFound("Veículo não encontrado"));


        if(veiculo.getStatus() != TipoStatusVeiculo.DISPONIVEL) throw new VeiculoNaoDisponivel();

        final var locacao = new Locacao();
            locacao.setUsuario(usuario);
            locacao.setVeiculo(veiculo);
            locacao.setDataInicio(locacaoDto.getDataInicio());
            locacao.setDataFim(locacaoDto.getDataFim());
            locacao.setValor(BigDecimal
                    .valueOf(getDias(locacaoDto.getDataInicio(), locacaoDto.getDataFim()))
                    .multiply(veiculo.getValorDiario()));
            locacao.setStatus(TipoStatusLocacao.PENDENTE);

            Locacao locacaoCriada = locacaoRepository.save(locacao);
            locacaoDto.setId(locacaoCriada.getId());
            locacaoDto.setValor(locacaoCriada.getValor());
            locacaoDto.setStatus(locacao.getStatus());
            locacaoDto.setDataCriacao(locacaoCriada.getDataCriacao());

            veiculoService.editarStatus(veiculo.getId(), TipoStatusVeiculo.LOCACAO_EM_ANALISE);
            return locacaoDto;
    }

    private long getDias(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        long diffEmMil = Math.abs(Duration.between(dataInicial, dataFinal).toMillis());

        return TimeUnit.DAYS.convert(diffEmMil, TimeUnit.MILLISECONDS);
    }

    public List<LocacaoDto> buscarLocacoesPorStatus(TipoStatusLocacao statusLocacao) {
        List<Locacao> locacoes = locacaoRepository.findLocacaoByStatus(statusLocacao);

        return locacoes.stream().map(this::converter).collect(Collectors.toList());
    }

    private LocacaoDto converter(Locacao locacao) {
        final var locacaoDto = new LocacaoDto();
        locacaoDto.setId(locacao.getId());
        locacaoDto.setUsuarioId(locacao.getUsuario().getId());
        locacaoDto.setVeiculoId(locacao.getVeiculo().getId());
        locacaoDto.setValor(locacao.getValor());
        locacaoDto.setStatus(locacao.getStatus());
        locacaoDto.setDataInicio(locacao.getDataInicio());
        locacaoDto.setDataFim(locacao.getDataFim());
        locacaoDto.setDataCriacao(locacao.getDataCriacao());

        return locacaoDto;
    }

    public LocacaoDto editarStatus(Long id, TipoStatusLocacao status) {
        var locacao = locacaoRepository.findById(id)
                .orElseThrow(() -> new NotFound("Locação não encontrada"));
        locacao.setStatus(status);

       switch (status) {
           case CANCELADA:
           case FINALIZADA:
               veiculoService.editarStatus(locacao.getVeiculo().getId(), TipoStatusVeiculo.DISPONIVEL);
           default:
               veiculoService.editarStatus(locacao.getVeiculo().getId(), TipoStatusVeiculo.LOCADO);
       }

        locacaoRepository.save(locacao);
        return converter(locacao);
    }
}
