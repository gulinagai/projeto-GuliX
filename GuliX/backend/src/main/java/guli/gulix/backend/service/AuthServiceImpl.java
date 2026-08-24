package guli.gulix.backend.service;

import guli.gulix.backend.dto.AuthResponseDTO;
import guli.gulix.backend.dto.LoginRequestDTO;
import guli.gulix.backend.dto.RefreshTokenRequestDTO;
import guli.gulix.backend.entity.RefreshToken;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.CredenciaisInvalidasException;
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
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(()->
                        new CredenciaisInvalidasException(
                                "Email ou senha inválidos."
                        ));

        boolean senhaValida = passwordEncoder.matches(
                dto.senha(),
                usuario.getSenhaHash()
        );

        if(!senhaValida) {
            throw new CredenciaisInvalidasException(
                    "Email ou senha inválidos."
            );
        }

        String token = jwtUtil.gerarToken(usuario);

        RefreshToken refreshToken = refreshTokenService.criarRefreshToken(usuario);

        return new AuthResponseDTO(token, refreshToken.getToken(), "Bearer");

    }


    @Override
    public AuthResponseDTO refresh(RefreshTokenRequestDTO dto) {

        RefreshToken refreshTokenOld = refreshTokenService.validarRefreshToken(dto.refreshToken());

        Usuario usuario = refreshTokenOld.getUsuario();

        String newJWTToken = jwtUtil.gerarToken(usuario);
        RefreshToken refreshTokenNew = refreshTokenService.criarRefreshToken(usuario);

        refreshTokenService.revogarRefreshToken(refreshTokenOld);

        return new AuthResponseDTO(newJWTToken, refreshTokenNew.getToken(), "Bearer");
    }


    @Override
    public void logout(RefreshTokenRequestDTO dto) {

        RefreshToken refreshToken = refreshTokenService.validarRefreshToken(dto.refreshToken());

        refreshTokenService.revogarRefreshToken(refreshToken);

    }

}
