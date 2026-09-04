package guli.gulix.backend.repository;

import guli.gulix.backend.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    @Query("""
    SELECT c
    FROM Cidade c
    WHERE UPPER(TRIM(c.nome)) = UPPER(TRIM(:nome))
""")
    Optional<Cidade> findByNome(@Param("nome") String nome);
}
