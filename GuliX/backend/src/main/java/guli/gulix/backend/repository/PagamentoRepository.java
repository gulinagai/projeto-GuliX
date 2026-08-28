package guli.gulix.backend.repository;

import guli.gulix.backend.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    Optional<Pagamento> findByGatewayCheckoutId(String gatewayCheckoutId);

    Optional<Pagamento> findByGatewayPaymentId(String gatewayPaymentId);

    Optional<Object> findByPedidoId(String id);
}
