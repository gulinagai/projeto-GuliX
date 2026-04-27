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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getListUsuario() {

        List<UsuarioResponseDTO> response = usuarioService.getListUsuario();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable("usuarioId") Integer usuarioId) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(usuarioId));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createNewUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioRequest) {

        UsuarioResponseDTO response = usuarioService.createNewUsuario(usuarioRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/usuarios/" + response.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }


    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuarioById(@PathVariable("usuarioId") Integer usuarioId, @RequestBody @Valid UsuarioUpdateDTO usuarioAtualizar) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.updateUsuarioById(usuarioId, usuarioAtualizar);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PatchMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> updatePatchUsuarioById(@PathVariable("usuarioId") Integer usuarioId, @RequestBody @Valid UsuarioUpdateDTO usuarioAtualizar) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.updatePatchUsuarioById(usuarioId, usuarioAtualizar);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping(("/{usuarioId}"))
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable("usuarioId") Integer usuarioId) {

        usuarioService.deleteUsuarioById(usuarioId);

        return ResponseEntity.noContent().build();

    }
}
