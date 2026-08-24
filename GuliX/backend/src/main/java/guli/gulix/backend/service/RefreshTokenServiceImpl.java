package guli.gulix.backend.service;

import guli.gulix.backend.entity.RefreshToken;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.CredenciaisInvalidasException;
import guli.gulix.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${refresh-token.expiration}")
    private long refreshTokenExpiration;


    @Override
    public RefreshToken criarRefreshToken(Usuario usuario) {

        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);


        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(token);
        refreshToken.setUsuario(usuario);
        refreshToken.setExpiracao(LocalDateTime.now().plusSeconds(
                refreshTokenExpiration / 1000
        ));
        refreshToken.setRevogado(false);

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken validarRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(()->
                        new CredenciaisInvalidasException("Refresh Token inválido")
                        );

        if (refreshToken.isRevogado()) {
            throw new CredenciaisInvalidasException("Refresh Token inválido");
        }

        if(!refreshToken.getExpiracao().isAfter(LocalDateTime.now())) {
            throw new CredenciaisInvalidasException("Refresh Token expirado");
        }

        return refreshToken;
    }

    @Override
    public void revogarRefreshToken(RefreshToken refreshToken) {
        refreshToken.setRevogado(true);
    }
}
