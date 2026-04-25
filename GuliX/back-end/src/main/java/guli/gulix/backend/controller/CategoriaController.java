package guli.gulix.backend.controller;


import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.dto.CategoriaResponseDTO;
import guli.gulix.backend.entity.Categoria;
import guli.gulix.backend.mapper.CategoriaMapper;
import guli.gulix.backend.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> getListCategoria() {

        List<Categoria> categorias = categoriaService.getListCategoria();

        List<CategoriaResponseDTO> response = categorias.stream().map(categoriaMapper::toDTO).toList();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaResponseDTO> getCategoriaById(@PathVariable("categoriaId") Integer categoriaId) {

        Categoria categoria = categoriaService.getCategoriaById(categoriaId);

        return ResponseEntity.ok(categoriaMapper.toDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createNewCategoria(@RequestBody CategoriaRequestDTO categoriaRequest) {

        Categoria novaCategoria = categoriaService.createNewCategoria(categoriaRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/categorias/" + novaCategoria.getId().toString());

        CategoriaResponseDTO response = categoriaMapper.toDTO(novaCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{categoriaId}")
    public ResponseEntity<CategoriaResponseDTO> updateCategoriaById(@PathVariable("categoriaId") Integer categoriaId, @RequestBody CategoriaRequestDTO categoriaAtualizar) {

        Categoria categoria = categoriaService.updateCategoriaById(categoriaId, categoriaAtualizar);

        return ResponseEntity.ok(categoriaMapper.toDTO(categoria));

    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Integer categoriaId) {

        categoriaService.deleteCategoriaById(categoriaId);

        return ResponseEntity.noContent().build();

    }
}
