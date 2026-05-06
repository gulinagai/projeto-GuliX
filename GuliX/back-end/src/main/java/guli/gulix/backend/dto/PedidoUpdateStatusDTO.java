package guli.gulix.backend.dto;

import guli.gulix.backend.entity.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateStatusDTO {

    private StatusPedido status;
}
