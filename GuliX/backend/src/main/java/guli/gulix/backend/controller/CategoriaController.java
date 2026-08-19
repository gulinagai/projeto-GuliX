package guli.gulix.backend.controller;


import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.dto.CategoriaResponseDTO;
import guli.gulix.backend.entity.Categoria;
import guli.gulix.backend.mapper.CategoriaMapper;
import guli.gulix.backend.service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> getListCategoria() {

        List<CategoriaResponseDTO> response = categoriaService.getListCategoria();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaResponseDTO> getCategoriaById(@PathVariable("categoriaId") Integer categoriaId) {
        return ResponseEntity.ok(categoriaService.getCategoriaById(categoriaId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> createNewCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequest) {

        CategoriaResponseDTO response = categoriaService.createNewCategoria(categoriaRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/categorias/" + response.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);

    }

    @PutMapping("/{categoriaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> updateCategoriaById(@PathVariable("categoriaId") Integer categoriaId, @Valid @RequestBody CategoriaRequestDTO categoriaAtualizar) {

        CategoriaResponseDTO response = categoriaService.updateCategoriaById(categoriaId, categoriaAtualizar);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{categoriaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Integer categoriaId) {

        categoriaService.deleteCategoriaById(categoriaId);

        return ResponseEntity.noContent().build();

    }
}
