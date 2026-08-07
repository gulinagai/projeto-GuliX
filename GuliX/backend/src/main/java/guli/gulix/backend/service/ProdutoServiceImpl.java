package guli.gulix.backend.service;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProdutoMapper produtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> getAllProduto() {
        return produtoRepository.findAll().stream().map(produtoMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoResponseDTO getProdutoById(Integer produtoId) {
        Produto produto =  produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                         "Produto com id " + produtoId + " não encontrado"
                        ));

        return produtoMapper.toDTO(produto);
    }

    @Override
    public ProdutoResponseDTO createNewProduto(ProdutoCreateDTO produtoRequest) {

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

        return produtoMapper.toDTO(produtoRepository.save(produto));
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
    public ProdutoResponseDTO updateProdutoById(Integer produtoId, ProdutoUpdateDTO produtoAtualizar) {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id " + produtoId + " não encontrado"
                        ));

        produtoMapper.updateEntityFromDto(produtoAtualizar, produto); // o Mapstruct gera todos os sets automaticamente! atualiza o que tiver para atualizar no objeto
        // basta gravar no banco
        // o fato de existir metodo no mapper tratando update, representa exatamente o que seria feito abaixo manualmente:

//        produto.setNome(produtoAtualizar.getNome());
//        produto.setResumo(produtoAtualizar.getResumo());
//        produto.setPreco(produtoAtualizar.getPreco());
//        produto.setEstoque(produtoAtualizar.getEstoque());
//        produto.setCategoriaId(produtoAtualizar.getCategoriaId());
//        produto.setImagemURL(produtoAtualizar.getImagemURL());
//        produto.setMarcaId(produtoAtualizar.getMarcaId());
//        produto.setDestaque(produtoAtualizar.getDestaque());
//        produto.setDesconto(produtoAtualizar.getDesconto());

        if(produtoAtualizar.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(produtoAtualizar.getCategoriaId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Categoria com id " + produtoAtualizar.getCategoriaId() + " não encontrado"
                            ));
            produto.setCategoria(categoria);
        }

        if(produtoAtualizar.getMarcaId() != null) {
            Marca marca = marcaRepository.findById(produtoAtualizar.getMarcaId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Marca com id " + produtoAtualizar.getMarcaId() + " não encontrado"
                            ));
            produto.setMarca(marca);
        }

        return produtoMapper.toDTO(produto);

    }

    @Override
    public ProdutoResponseDTO updatePartialProdutoById(Integer produtoId, ProdutoUpdateDTO produtoAtualizar) {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id " + produtoId + " não encontrado"
                        ));

        produtoMapper.updateEntityFromDto(produtoAtualizar, produto); // o Mapstruct gera todos os sets automaticamente! atualiza o que tiver para atualizar no objeto
        // basta gravar no banco
        // o fato de no mapper existir tratamento para ignorar null, é exatamente o que seria feito abaixo manualmente:

//        if(produtoParcial.getNome() != null) {
//            produto.setNome(produtoParcial.getNome());
//        }
//
//        if(produtoParcial.getResumo() != null) {
//            produto.setResumo(produtoParcial.getResumo());
//        }
//
//        if(produtoParcial.getPreco() != null) {
//            produto.setPreco(produtoParcial.getPreco());
//        }
//
//        if(produtoParcial.getEstoque() != null) {
//            produto.setEstoque(produtoParcial.getEstoque());
//        }
//
//        if(produtoParcial.getImagemURL() != null) {
//            produto.setImagemURL(produtoParcial.getImagemURL());
//        }
//
//        if(produtoParcial.getDestaque() != null) {
//            produto.setDestaque(produtoParcial.getDestaque());
//        }
//
//        if(produtoParcial.getDesconto() != null) {
//            produto.setDesconto(produtoParcial.getDesconto());
//        }

        if(produtoAtualizar.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(produtoAtualizar.getCategoriaId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Categoria com id " + produtoAtualizar.getCategoriaId() + " não encontrado"
                            ));
            produto.setCategoria(categoria);
        }

        if(produtoAtualizar.getMarcaId() != null) {
            Marca marca = marcaRepository.findById(produtoAtualizar.getMarcaId())
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Marca com id " + produtoAtualizar.getMarcaId() + " não encontrado"
                            ));
            produto.setMarca(marca);
        }

        return produtoMapper.toDTO(produto);
    }

}
