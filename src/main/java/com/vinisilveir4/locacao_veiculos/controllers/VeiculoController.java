package com.vinisilveir4.locacao_veiculos.controllers;

import com.vinisilveir4.locacao_veiculos.dtos.VeiculoDto;
import com.vinisilveir4.locacao_veiculos.services.VeiculoService;
import com.vinisilveir4.locacao_veiculos.util.TipoStatusVeiculo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("veiculo")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping("/novo")
    public ResponseEntity<String> novoVeiculo(@RequestBody @Valid VeiculoDto veiculoDto) {
        String msg = veiculoService.cadastrar(veiculoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<HttpStatus> removerVeiculo(@PathVariable(name = "id") Long id) {
            veiculoService.remover(id);

            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<HttpStatus> atualizarStatus(@PathVariable(name = "id") Long id, @RequestParam(name = "status") TipoStatusVeiculo status) {
            veiculoService.editarStatus(id, status);

            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<VeiculoDto>> listarVeiculosDisponiveis() {
        final var veiculos = veiculoService.veiculosDisponiveis();

        return ResponseEntity.ok().body(veiculos);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<VeiculoDto>> listarTodosVeiculos() {
        final var veiculos = veiculoService.todosVeiculos();

        return ResponseEntity.ok().body(veiculos);
    }
}
