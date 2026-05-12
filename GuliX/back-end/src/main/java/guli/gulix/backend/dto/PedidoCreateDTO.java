package guli.gulix.backend.dto;

import guli.gulix.backend.entity.Endereco;
import guli.gulix.backend.entity.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateDTO {
    private Integer enderecoId;
    private MetodoPagamento metodoPagamento;
    private Integer numeroParcelas;
}


