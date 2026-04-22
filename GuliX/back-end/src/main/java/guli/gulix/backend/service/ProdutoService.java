package guli.gulix.backend.service;

import guli.gulix.backend.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    List<Produto> getAllProduto();

    Produto getProdutoById(Integer produtoId);

    Produto createNewProduto(Produto produto);

    void deleteProdutoById(Integer produtoId);

    Produto updateProdutoById(Integer produtoId, Produto produto);
}
