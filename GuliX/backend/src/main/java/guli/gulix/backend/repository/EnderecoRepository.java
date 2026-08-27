package guli.gulix.backend.repository;

import guli.gulix.backend.entity.Endereco;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByUsuarioId(Integer usuarioId);

    Optional<Endereco> findByIdAndUsuario(Integer enderecoId, Usuario usuario);
}
