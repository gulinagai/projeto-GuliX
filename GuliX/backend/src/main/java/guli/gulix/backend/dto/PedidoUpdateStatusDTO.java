package guli.gulix.backend.dto;

import guli.gulix.backend.entity.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateStatusDTO {
    @NotNull(message = "O status do pedido é obrigatório")
    private StatusPedido status;
}
