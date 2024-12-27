package com.vinisilveir4.locacao_veiculos.controllers;

import com.vinisilveir4.locacao_veiculos.dtos.LocacaoDto;
import com.vinisilveir4.locacao_veiculos.models.Locacao;
import com.vinisilveir4.locacao_veiculos.services.LocacaoService;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusLocacao;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusVeiculo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locacao")
@RequiredArgsConstructor
public class LocacaoController {

    private final LocacaoService locacaoService;

    @PostMapping("/solicitar")
    public ResponseEntity<LocacaoDto> solicitarLocacao(@RequestBody LocacaoDto locacaoDto) {
            LocacaoDto locacao = locacaoService.locarVeiculo(locacaoDto);

            return ResponseEntity.status(HttpStatus.OK).body(locacao);
    }

    @GetMapping("/visualizar")
    public ResponseEntity<List<LocacaoDto>> buscarLocacoes(@RequestParam(name = "status")TipoStatusLocacao statusLocacao) {
        final var locacoes = locacaoService.buscarLocacoesPorStatus(statusLocacao);

        return ResponseEntity.ok().body(locacoes);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<LocacaoDto> atualizarStatus(@PathVariable(name = "id") Long id, @RequestParam(name = "status") TipoStatusLocacao status) {
            LocacaoDto locacaoDto = locacaoService.editarStatus(id, status);

            return ResponseEntity.status(HttpStatus.OK).body(locacaoDto);
    }
}
