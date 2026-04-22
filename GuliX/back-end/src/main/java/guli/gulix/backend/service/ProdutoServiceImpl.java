package guli.gulix.backend.service;

import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> getAllProduto() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto getProdutoById(Integer produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                         "Produto com id " + produtoId + " não encontrado"
                        ));
    }

    @Override
    public Produto createNewProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public void deleteProdutoById(Integer produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id " + produtoId + " não encontrado"
                        ));
        produtoRepository.delete(produto);
    }

    @Override
    public Produto updateProdutoById(Integer produtoId, Produto produtoAtualizar) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id " + produtoId + " não encontrado"
                        ));

        produto.setNome(produtoAtualizar.getNome());
        produto.setResumo(produtoAtualizar.getResumo());
        produto.setPreco(produtoAtualizar.getPreco());
        produto.setEstoque(produtoAtualizar.getEstoque());
        produto.setCategoriaId(produtoAtualizar.getCategoriaId());
        produto.setImagemURL(produtoAtualizar.getImagemURL());
        produto.setMarcaId(produtoAtualizar.getMarcaId());
        produto.setDestaque(produtoAtualizar.getDestaque());
        produto.setDesconto(produtoAtualizar.getDesconto());

        return produtoRepository.save(produto);

    }
}
