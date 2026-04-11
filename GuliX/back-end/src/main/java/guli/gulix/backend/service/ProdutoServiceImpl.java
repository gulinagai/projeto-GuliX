package guli.gulix.backend.service;

import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> getAllProduto() {
        return produtoRepository.findAll();
    }
}
