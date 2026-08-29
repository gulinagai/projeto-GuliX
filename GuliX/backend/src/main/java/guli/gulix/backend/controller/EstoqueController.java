package guli.gulix.backend.controller;


import guli.gulix.backend.dto.EstoqueRequestDTO;
import guli.gulix.backend.dto.EstoqueRequestInventarioDTO;
import guli.gulix.backend.dto.EstoqueResponseDTO;
import guli.gulix.backend.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @GetMapping
    ResponseEntity<List<EstoqueResponseDTO>> getAllEstoque() {

        return ResponseEntity.ok(estoqueService.getAllEstoque());
    }

    @GetMapping("/{produtoId}")
    ResponseEntity<EstoqueResponseDTO> getEstoqueByProdutoId(@PathVariable("produtoId") Integer produtoId) {
        return ResponseEntity.ok(estoqueService.getEstoqueByProdutoId(produtoId));
    }

    @PutMapping("/{produtoId}/entrada")
    ResponseEntity<EstoqueResponseDTO> adicionarEstoque(@PathVariable("produtoId") Integer produtoId, @RequestBody EstoqueRequestDTO estoqueRequest) {
        return ResponseEntity.ok(estoqueService.adicionarEstoque(produtoId, estoqueRequest));
    }

    @PutMapping("/{produtoId}/saida")
    ResponseEntity<EstoqueResponseDTO> removerEstoque(@PathVariable("produtoId") Integer produtoId, @RequestBody EstoqueRequestDTO estoqueRequest) {
        return ResponseEntity.ok(estoqueService.removerEstoque(produtoId, estoqueRequest));
    }

    @PutMapping("/{produtoId}/inventario")
    ResponseEntity<EstoqueResponseDTO> realizarInventario(@PathVariable("produtoId") Integer produtoId, @RequestBody EstoqueRequestInventarioDTO estoqueRequest) {
        return ResponseEntity.ok(estoqueService.realizarInventario(produtoId, estoqueRequest));
    }

}