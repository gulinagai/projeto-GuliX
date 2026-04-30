package guli.gulix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinhoResponseDTO {
    private Integer id;
    private String nomeProduto;
    private BigDecimal preco;
    private Integer quantidade;
}


