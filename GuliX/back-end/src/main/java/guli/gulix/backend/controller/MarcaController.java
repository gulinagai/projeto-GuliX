package guli.gulix.backend.controller;




import guli.gulix.backend.dto.MarcaRequestDTO;
import guli.gulix.backend.dto.MarcaResponseDTO;

import guli.gulix.backend.entity.Marca;

import guli.gulix.backend.mapper.MarcaMapper;

import guli.gulix.backend.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {
    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> getListMarca() {

        List<MarcaResponseDTO> response = marcaService.getListMarca();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{marcaId}")
    public ResponseEntity<MarcaResponseDTO> getMarcaById(@PathVariable("marcaId") Integer marcaId) {
        return ResponseEntity.ok(marcaService.getMarcaById(marcaId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MarcaResponseDTO> createNewMarca(@RequestBody MarcaRequestDTO marcaRequest) {

        MarcaResponseDTO response = marcaService.createNewMarca(marcaRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/marcas/" + response.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{marcaId}")
    public ResponseEntity<MarcaResponseDTO> updateMarcaById(@PathVariable("marcaId") Integer marcaId, @RequestBody MarcaRequestDTO marcaAtualizar) {

        MarcaResponseDTO response = marcaService.updateMarcaById(marcaId, marcaAtualizar);

        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{marcaId}")
    public ResponseEntity<Void> deleteMarcaById(@PathVariable Integer marcaId) {

        marcaService.deleteMarcaById(marcaId);

        return ResponseEntity.noContent().build();

    }

}