package guli.gulix.backend.dto;

import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {

    private Long id;

    private Integer produtoId;

    private String nomeProduto;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;
}
