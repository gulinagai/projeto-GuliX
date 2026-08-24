package guli.gulix.backend.service;

import guli.gulix.backend.dto.AuthResponseDTO;
import guli.gulix.backend.dto.LoginRequestDTO;
import guli.gulix.backend.dto.RefreshTokenRequestDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO dto);

    AuthResponseDTO refresh(RefreshTokenRequestDTO dto);

    void logout(RefreshTokenRequestDTO dto);
}
