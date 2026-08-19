package guli.gulix.backend.controller;

import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<EnderecoResponseDTO>> getListEnderecos(
            @AuthenticationPrincipal Usuario usuario
            ) {
        return ResponseEntity.ok(enderecoService.getListEnderecos(usuario));
    }

    @GetMapping("/{enderecoId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EnderecoResponseDTO> getEnderecoById(
            @PathVariable("enderecoId") Integer enderecoId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(enderecoService.getEnderecoById(enderecoId, usuario));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EnderecoResponseDTO> createNewEndereco(
            @Valid @RequestBody EnderecoCreateDTO enderecoRequest,
            @AuthenticationPrincipal Usuario usuario
    ) {

        EnderecoResponseDTO novoEndereco = enderecoService.createNewEndereco(enderecoRequest, usuario);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/enderecos/" + novoEndereco.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(novoEndereco);
    }

    @DeleteMapping("/{enderecoId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteEnderecoById(
            @PathVariable("enderecoId") Integer enderecoId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        enderecoService.deleteEnderecoById(enderecoId, usuario);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{enderecoId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EnderecoResponseDTO> updateEnderecoById(
            @PathVariable("enderecoId") Integer enderecoId,
            @Valid @RequestBody EnderecoUpdateDTO enderecoAtualizar,
            @AuthenticationPrincipal Usuario usuario
    ) {

        EnderecoResponseDTO enderecoAtualizado = enderecoService.updateEnderecoById(enderecoId, enderecoAtualizar, usuario);

        return ResponseEntity.ok(enderecoAtualizado);
    }

    @PatchMapping("/{enderecoId}/principal")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updateEnderecoPrincipalById(
            @PathVariable("enderecoId") Integer enderecoId,
            @AuthenticationPrincipal Usuario usuario
    ) {

        enderecoService.updateEnderecoPrincipalById(enderecoId, usuario);

        return ResponseEntity.noContent().build();
    }


}
