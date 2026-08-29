package guli.gulix.backend.repository;

import guli.gulix.backend.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    Optional<Estoque> findByProdutoId(Integer produtoId);
}
