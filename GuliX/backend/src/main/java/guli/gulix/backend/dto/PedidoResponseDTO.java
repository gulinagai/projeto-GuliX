package guli.gulix.backend.dto;

import guli.gulix.backend.entity.EnderecoEntrega;
import guli.gulix.backend.entity.ItemPedido;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.entity.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private Integer id;

    private Integer usuarioId;

    private StatusPedido statusPedido;

    private BigDecimal total;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    private EnderecoEntregaDTO enderecoEntrega;

    private List<ItemPedidoResponseDTO> itens;

}
