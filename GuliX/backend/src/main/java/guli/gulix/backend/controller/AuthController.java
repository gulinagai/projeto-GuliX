package guli.gulix.backend.controller;

import guli.gulix.backend.dto.*;
import guli.gulix.backend.service.AuthService;
import guli.gulix.backend.service.RefreshTokenService;
import guli.gulix.backend.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> createNewUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioRequest) {

        UsuarioResponseDTO response = usuarioService.createNewUsuario(usuarioRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/usuarios/" + response.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(
            @RequestBody RefreshTokenRequestDTO dto
            ) {
        return ResponseEntity.ok(authService.refresh(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestBody RefreshTokenRequestDTO dto
    ) {
        authService.logout(dto);

        return ResponseEntity.noContent().build();
    }
}




