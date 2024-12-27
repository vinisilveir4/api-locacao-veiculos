package com.vinisilveir4.locacao_veiculos.controllers;

import com.vinisilveir4.locacao_veiculos.dtos.UsuarioDto;
import com.vinisilveir4.locacao_veiculos.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/adicionar-vendedor")
    public ResponseEntity<String> novoVendedor(@RequestBody UsuarioDto usuarioDto) {
            boolean vendedor = true;
            Long id = usuarioService.cadastrarVendedor(usuarioDto, vendedor);
            String msg = String.format("Id: %d%n", id);

            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
}
