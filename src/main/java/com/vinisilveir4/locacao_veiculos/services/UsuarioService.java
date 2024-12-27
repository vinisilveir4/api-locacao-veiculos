package com.vinisilveir4.locacao_veiculos.services;

import com.vinisilveir4.locacao_veiculos.config.exceptionHandler.ConflitoDeDado;
import com.vinisilveir4.locacao_veiculos.dtos.UsuarioDto;
import com.vinisilveir4.locacao_veiculos.models.Permissao;
import com.vinisilveir4.locacao_veiculos.models.Usuario;
import com.vinisilveir4.locacao_veiculos.repositories.PermissaoRepository;
import com.vinisilveir4.locacao_veiculos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PermissaoRepository permissaoRepository;
    private final UsuarioRepository usuarioRepository;

    public Long cadastrarUsuario(UsuarioDto usuarioDto, boolean vendedor) {
        var emailExiste = usuarioRepository
                .findUsuarioByEmail(usuarioDto.getEmail())
                .isPresent();

        var cpfExiste = usuarioRepository
                .findUsuarioByCpf(usuarioDto.getCpf())
                .isPresent();

        if(emailExiste)
            throw new ConflitoDeDado("Email já cadastrado");

        if(cpfExiste)
            throw new ConflitoDeDado("Cpf já cadastrado");

        var usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setSobrenome(usuarioDto.getSobrenome());
        usuario.setNumero(usuarioDto.getNumero());
        usuario.setEndereco(usuarioDto.getEndereco());
        usuario.setCep(usuarioDto.getCep());
        usuario.setCpf(usuarioDto.getCpf());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.getSenha()));

        Set<Permissao> permissoes = new HashSet<>();
        if(vendedor) {
            var permissaoVendedor = permissaoRepository
                    .findPermissaoByNome("VENDEDOR")
                    .orElse(null);

            permissoes.add(permissaoVendedor);
        } else {
            var permissaoUsuario = permissaoRepository
                    .findPermissaoByNome("USUARIO")
                    .orElse(null);

            permissoes.add(permissaoUsuario);
        }

        usuario.setPermissoes(permissoes);
        Usuario usuarioCriado = usuarioRepository.save(usuario);

        return usuarioCriado.getId();
    }

    public Optional<UsuarioDto> buscarUsuario(String email) {
        return usuarioRepository
                .findUsuarioByEmail(email)
                .map(usuario -> {
                    var usuarioDto = new UsuarioDto();
                    usuarioDto.setId(usuario.getId());
                    usuarioDto.setEmail(usuario.getEmail());
                    usuarioDto.setSenha(usuario.getSenha());

                    Set<String> permissoes = new HashSet<>();

                    usuario.getPermissoes().forEach(permissao -> permissoes.add(permissao.getNome()));
                    usuarioDto.setPermissoes(permissoes);

                    return usuarioDto;
                });
    }

    public Long cadastrarVendedor(UsuarioDto usuarioDto, boolean vendedor) {
        return cadastrarUsuario(usuarioDto, vendedor);
    }
}
