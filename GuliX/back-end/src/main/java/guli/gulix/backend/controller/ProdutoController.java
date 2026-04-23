package guli.gulix.backend.controller;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.mapper.ProdutoMapper;
import guli.gulix.backend.repository.ProdutoRepository;
import guli.gulix.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    @GetMapping
    public ResponseEntity<List<Produto>> getAllProduto() {
        return ResponseEntity.ok(produtoService.getAllProduto());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable("produtoId") Integer produtoId) {
        Produto produto = produtoService.getProdutoById(produtoId);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createNewProduto(@RequestBody ProdutoCreateDTO produtoRequest) {

        Produto novoProduto = produtoService.createNewProduto(produtoRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/beer/" + novoProduto.getId().toString());

        ProdutoResponseDTO response = produtoMapper.toDTO(novoProduto);


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deleteProdutoById(@PathVariable("produtoId") Integer produtoId) {
        produtoService.deleteProdutoById(produtoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<Produto> updateProdutoById(@PathVariable("produtoId") Integer produtoId, @RequestBody Produto produtoAtualizar) {

        Produto produtoAtualizado = produtoService.updateProdutoById(produtoId, produtoAtualizar);

        return ResponseEntity.ok(produtoAtualizado);
    }

    @PatchMapping("/{produtoId}")
    public ResponseEntity<Produto> updatePartialProdutoById(@PathVariable("produtoId") Integer produtoId, @RequestBody Produto produtoParcial) {

       Produto produtoAtualizado = produtoService.updatePartialProdutoById(produtoId, produtoParcial);

       return ResponseEntity.ok(produtoAtualizado);
    }

}
