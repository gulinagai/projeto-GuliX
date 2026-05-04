package guli.gulix.backend.controller;

import guli.gulix.backend.dto.UsuarioCreateDTO;
import guli.gulix.backend.dto.UsuarioResponseDTO;
import guli.gulix.backend.dto.UsuarioUpdateDTO;

import guli.gulix.backend.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getListUsuario() {

        List<UsuarioResponseDTO> response = usuarioService.getListUsuario();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or #usuarioId == authentication.principal.id")
    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable("usuarioId") Integer usuarioId) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(usuarioId));
    }


    @PreAuthorize("hasRole('ADMIN') or #usuarioId == authentication.principal.id")
    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuarioById(@PathVariable("usuarioId") Integer usuarioId, @RequestBody @Valid UsuarioUpdateDTO usuarioAtualizar) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.updateUsuarioById(usuarioId, usuarioAtualizar);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PreAuthorize("hasRole('ADMIN') or #usuarioId == authentication.principal.id")
    @PatchMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> updatePatchUsuarioById(@PathVariable("usuarioId") Integer usuarioId, @RequestBody @Valid UsuarioUpdateDTO usuarioAtualizar) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.updatePatchUsuarioById(usuarioId, usuarioAtualizar);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(("/{usuarioId}"))
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable("usuarioId") Integer usuarioId) {

        usuarioService.deleteUsuarioById(usuarioId);

        return ResponseEntity.noContent().build();

    }
}
