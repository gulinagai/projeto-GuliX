package guli.gulix.backend.dto;

import guli.gulix.backend.entity.enums.MetodoPagamento;
import guli.gulix.backend.entity.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoResponseDTO {

    private Integer id;
    private Integer pedidoId;

    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;

    private BigDecimal valorOriginal;
    private BigDecimal desconto;
    private BigDecimal percentualJuros;
    private BigDecimal valorJuros;
    private BigDecimal valorFinal;

    private Integer numeroParcelas;
    private BigDecimal valorParcela;

    private String checkoutUrl;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

}
