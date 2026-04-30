package guli.gulix.backend.service;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.dto.ItemCarrinhoRequestDTO;
import guli.gulix.backend.dto.ItemCarrinhoUpdateDTO;
import guli.gulix.backend.entity.Carrinho;
import guli.gulix.backend.entity.ItemCarrinho;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
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

    @Transactional(readOnly = true)
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


        if(item != null) {
            item.setQuantidade(item.getQuantidade() + itemDTO.getQuantidade());
        } else {
            ItemCarrinho novo = new ItemCarrinho();
            novo.setCarrinho(carrinho);
            novo.setProduto(produto);
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
             throw new IllegalArgumentException("Quantidade deve ser maior que zero");
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

}
