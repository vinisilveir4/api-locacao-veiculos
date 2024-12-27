package com.vinisilveir4.locacao_veiculos.services;

import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.NotFound;
import com.vinisilveir4.locacao_veiculos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        final var usuario = usuarioService.buscarUsuario(email)
                .orElseThrow(() -> new NotFound("Usuário não foi encontrado!"));


        Set<SimpleGrantedAuthority> permissoes = usuario.getPermissoes().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(usuario.getEmail(), usuario.getSenha(), permissoes);
    }
}
