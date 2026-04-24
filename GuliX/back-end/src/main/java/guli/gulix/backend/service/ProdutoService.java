package guli.gulix.backend.service;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    List<Produto> getAllProduto();

    Produto getProdutoById(Integer produtoId);

    Produto createNewProduto(ProdutoCreateDTO produtoRequest);

    void deleteProdutoById(Integer produtoId);

    Produto updateProdutoById(Integer produtoId, ProdutoUpdateDTO produto);

    Produto updatePartialProdutoById(Integer produtoId, ProdutoUpdateDTO produtoParcial);
}
