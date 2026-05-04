package guli.gulix.backend.service;

import guli.gulix.backend.dto.AuthResponseDTO;
import guli.gulix.backend.dto.LoginRequestDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.repository.UsuarioRepository;
import guli.gulix.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Credenciais inválidas"
                        ));

        boolean senhaValida = passwordEncoder.matches(
                dto.senha(),
                usuario.getSenhaHash()
        );

        if(!senhaValida) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtUtil.gerarToken(usuario);

        return new AuthResponseDTO(token, "Bearer");

    }
}
