package guli.gulix.backend.service;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.entity.Categoria;
import guli.gulix.backend.entity.Marca;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.ProdutoMapper;
import guli.gulix.backend.repository.CategoriaRepository;
import guli.gulix.backend.repository.MarcaRepository;
import guli.gulix.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProdutoMapper produtoMapper;

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
    public Produto createNewProduto(ProdutoCreateDTO produtoRequest) {

        Produto produto = produtoMapper.toEntity(produtoRequest);

        if(produtoRequest.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(produtoRequest.getCategoriaId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Categoria com id " + produtoRequest.getCategoriaId() + " não encontrado"
                            ));
            produto.setCategoria(categoria);
        }

        if(produtoRequest.getMarcaId() != null) {
            Marca marca = marcaRepository.findById(produtoRequest.getMarcaId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Marca com id " + produtoRequest.getMarcaId() + " não encontrado"
                            ));
            produto.setMarca(marca);
        }

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

    @Override
    public Produto updatePartialProdutoById(Integer produtoId, Produto produtoParcial) {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id " + produtoId + " não encontrado"
                        ));

        if(produtoParcial.getNome() != null) {
            produto.setNome(produtoParcial.getNome());
        }

        if(produtoParcial.getResumo() != null) {
            produto.setResumo(produtoParcial.getResumo());
        }

        if(produtoParcial.getPreco() != null) {
            produto.setPreco(produtoParcial.getPreco());
        }

        if(produtoParcial.getEstoque() != null) {
            produto.setEstoque(produtoParcial.getEstoque());
        }

        if(produtoParcial.getImagemURL() != null) {
            produto.setImagemURL(produtoParcial.getImagemURL());
        }

        if(produtoParcial.getDestaque() != null) {
            produto.setDestaque(produtoParcial.getDestaque());
        }

        if(produtoParcial.getDesconto() != null) {
            produto.setDesconto(produtoParcial.getDesconto());
        }

        if(produtoParcial.getCategoriaId() != null) {

            Categoria categoria = categoriaRepository.findById(produtoParcial.getId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Produto com id " + produtoId + " não encontrado"
                            ));

            produto.setCategoria(produtoParcial.getCategoriaId());
        }

        if(produtoParcial.getMarcaId() != null) {
            produto.setMarcaId(produtoParcial.getMarcaId());
        }

        return produtoRepository.save(produto);
    }

}
