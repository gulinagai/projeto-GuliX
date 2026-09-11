package guli.gulix.backend.repository;


import guli.gulix.backend.entity.Filial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilialRepository extends JpaRepository<Filial, Integer> {
    Optional<Filial> findFirstBy();
}
