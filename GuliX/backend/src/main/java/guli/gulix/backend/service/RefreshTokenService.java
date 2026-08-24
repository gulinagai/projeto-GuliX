package guli.gulix.backend.service;

import guli.gulix.backend.entity.RefreshToken;
import guli.gulix.backend.entity.Usuario;

public interface RefreshTokenService {

    RefreshToken criarRefreshToken(Usuario usuario);

    RefreshToken validarRefreshToken(String token);

    void revogarRefreshToken(RefreshToken refreshToken);
}
