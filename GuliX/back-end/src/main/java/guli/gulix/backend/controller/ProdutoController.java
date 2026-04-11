package guli.gulix.backend.controller;

import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/produto")
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping
    public List<Produto> getAllProduto() {
        return produtoService.getAllProduto();
    }

}
