package guli.gulix.backend.repository;

import guli.gulix.backend.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {

    Optional<Carrinho> findByUsuarioId(Integer usuarioId);

}
