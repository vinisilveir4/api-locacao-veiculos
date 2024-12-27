package com.vinisilveir4.locacao_veiculos.controllers;

import com.vinisilveir4.locacao_veiculos.config.JwtUtil;
import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.ErroLogin;
import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.NotFound;
import com.vinisilveir4.locacao_veiculos.dtos.AuthDto;
import com.vinisilveir4.locacao_veiculos.dtos.UsuarioDto;
import com.vinisilveir4.locacao_veiculos.services.AuthorizationService;
import com.vinisilveir4.locacao_veiculos.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final AuthorizationService authorizationService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid UsuarioDto usuario) {
            boolean vendedor = false;
            usuarioService.cadastrarUsuario(usuario, vendedor);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody AuthDto authDto) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authDto.getEmail(), authDto.getSenha()
                    ));

            User user = (User) auth.getPrincipal();

            System.out.println(user);

            final var userDetails = authorizationService.loadUserByUsername(authDto.getEmail());

            UsuarioDto usuarioDto;
            try {
                usuarioDto = usuarioService.buscarUsuario(authDto.getEmail())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            } catch (RuntimeException ex) {
                throw new NotFound(ex.getMessage());
            }

            authDto.setId(usuarioDto.getId());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(userDetails)
                    )
                    .body(authDto);

        } catch(AuthenticationException ex) {
            System.out.println("teste");
            throw new ErroLogin(ex.getMessage());
        }
    }

    @GetMapping("/teste")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String testeAuth() {
        return "Admin";
    }

}