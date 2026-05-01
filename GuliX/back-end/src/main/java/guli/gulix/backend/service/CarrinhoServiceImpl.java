package guli.gulix.backend.service;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.dto.ItemCarrinhoRequestDTO;
import guli.gulix.backend.dto.ItemCarrinhoUpdateDTO;
import guli.gulix.backend.entity.Carrinho;
import guli.gulix.backend.entity.ItemCarrinho;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.mapper.CarrinhoMapper;
import guli.gulix.backend.repository.CarrinhoRepository;
import guli.gulix.backend.repository.ItemCarrinhoRepository;
import guli.gulix.backend.repository.ProdutoRepository;
import guli.gulix.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Transactional
public class CarrinhoServiceImpl implements CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoMapper carrinhoMapper;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;

    @Transactional
    public CarrinhoResponseDTO buscarCarrinho(Integer usuarioId) {

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseGet(()-> criarCarrinho(usuarioId));

        CarrinhoResponseDTO dto = carrinhoMapper.toDTO(carrinho);

        dto.setTotal(calcularTotal(carrinho));

        return dto;
    }



    public void adicionarItem(Integer usuarioId, ItemCarrinhoRequestDTO itemDTO) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseGet(()-> criarCarrinho(usuarioId));

        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto não encontrado"
                        ));

        ItemCarrinho item = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto)
                .orElse(null);

        validarQuantidadeDTO(itemDTO.getQuantidade());

        if(item != null) {
            Integer novaQuantidade = item.getQuantidade() + itemDTO.getQuantidade();

            validarEstoque(novaQuantidade, produto.getEstoque());
            item.setQuantidade(novaQuantidade);

        } else {
            ItemCarrinho novo = new ItemCarrinho();
            novo.setCarrinho(carrinho);
            novo.setProduto(produto);
            validarEstoque(itemDTO.getQuantidade(), produto.getEstoque());
            novo.setQuantidade(itemDTO.getQuantidade());



            itemCarrinhoRepository.save(novo);
        }
    }



    public void atualizarQuantidade(Integer usuarioId, Integer itemId, ItemCarrinhoUpdateDTO dto) {
         Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                 .orElseThrow(()->
                         new RecursoNaoEncontradoException(
                                 "Carrinho não encontrado."
                         ));

         ItemCarrinho item = itemCarrinhoRepository.findByIdAndCarrinho(itemId, carrinho)
                 .orElseThrow(()->
                         new RecursoNaoEncontradoException(
                                 "Item do carrinho de id " +  itemId + " não encontrado no carrinho."
                         ));

         if(dto.getQuantidade() <= 0) {
             throw new RegraNegocioException("Quantidade deve ser maior que zero");
         }

         item.setQuantidade(dto.getQuantidade());

    }

    public void removerItem(Integer usuarioId, Integer itemId) {

       Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
               .orElseThrow(()->
                       new RecursoNaoEncontradoException(
                               "Carrinho não encontrado."
                       ));

       ItemCarrinho item = itemCarrinhoRepository.findByIdAndCarrinho(itemId, carrinho)
               .orElseThrow(()->
                       new RecursoNaoEncontradoException(
                               "Item do carrinho de id " +  itemId + " não encontrado no carrinho."
                       ));

       itemCarrinhoRepository.delete(item);

    }


    private BigDecimal calcularTotal(Carrinho carrinho) {

        if(carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return carrinho.getItens().stream().map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Carrinho criarCarrinho(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id" + usuarioId + " não encontrado"
                        ));

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);

        return carrinhoRepository.save(carrinho);
    }

    private void validarEstoque(Integer quantidadeItem, Long quantidadeEstoque) {
        // conversão para tipo primitivo para não gerar inconsistência por comparação entre tipos Wrapper. Comparação entre tipos primitivos é segura.
        int quantItem = quantidadeItem;
        long quantEst = quantidadeEstoque;


        if(!(quantEst > 0)) {
            throw new RegraNegocioException("Estoque zerado para este produto");
        }

        if(!(quantItem <= quantEst)) {
            throw new RegraNegocioException("Quantidade solicitada maior que a quantidade disponível em estoque");
        }
    }

    private void validarQuantidadeDTO(Integer quantidade) {
        if(quantidade <= 0) {
            throw new RegraNegocioException("Quantidade solicitada menor ou igual a zero");
        }
    }

}
