package guli.gulix.backend.service;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    List<ProdutoResponseDTO> getAllProduto();

    ProdutoResponseDTO getProdutoById(Integer produtoId);

    ProdutoResponseDTO createNewProduto(ProdutoCreateDTO produtoRequest);

    void deleteProdutoById(Integer produtoId);

    ProdutoResponseDTO updateProdutoById(Integer produtoId, ProdutoUpdateDTO produto);

    ProdutoResponseDTO updatePartialProdutoById(Integer produtoId, ProdutoUpdateDTO produtoParcial);
}
