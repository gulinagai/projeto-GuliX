package guli.gulix.backend.controller;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.mapper.ProdutoMapper;
import guli.gulix.backend.repository.ProdutoRepository;
import guli.gulix.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProduto() {
        return ResponseEntity.ok(produtoService.getAllProduto());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable("produtoId") Integer produtoId) {
        return ResponseEntity.ok(produtoService.getProdutoById(produtoId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createNewProduto(@RequestBody ProdutoCreateDTO produtoRequest) {

        ProdutoResponseDTO novoProduto = produtoService.createNewProduto(produtoRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/produtos/" + novoProduto.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(novoProduto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deleteProdutoById(@PathVariable("produtoId") Integer produtoId) {
        produtoService.deleteProdutoById(produtoId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> updateProdutoById(@PathVariable("produtoId") Integer produtoId, @RequestBody ProdutoUpdateDTO produtoAtualizar) {

        ProdutoResponseDTO produtoAtualizado = produtoService.updateProdutoById(produtoId, produtoAtualizar);

        return ResponseEntity.ok(produtoAtualizado);
    }

    @PatchMapping("/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> updatePartialProdutoById(@PathVariable("produtoId") Integer produtoId, @RequestBody ProdutoUpdateDTO produtoAtualizar) {

        ProdutoResponseDTO produtoAtualizado = produtoService.updatePartialProdutoById(produtoId, produtoAtualizar);

       return ResponseEntity.ok(produtoAtualizado);
    }

}
