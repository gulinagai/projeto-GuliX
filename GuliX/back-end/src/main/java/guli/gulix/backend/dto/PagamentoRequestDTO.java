package guli.gulix.backend.dto;

import guli.gulix.backend.entity.enums.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequestDTO {
    @NotNull
    private Integer pedidoId;

    @NotNull
    private MetodoPagamento metodoPagamento;

    @NotNull
    private BigDecimal valor;
}
