package guli.gulix.backend.repository;

import guli.gulix.backend.entity.Carrinho;
import guli.gulix.backend.entity.ItemCarrinho;
import guli.gulix.backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Integer> {
    Optional<ItemCarrinho> findByCarrinhoAndProduto(Carrinho carrinho, Produto produto);

    Optional<ItemCarrinho> findByIdAndCarrinho(Integer itemId, Carrinho carrinho);
}

