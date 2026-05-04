package guli.gulix.backend.service;

import guli.gulix.backend.dto.AuthResponseDTO;
import guli.gulix.backend.dto.LoginRequestDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO dto);
}
