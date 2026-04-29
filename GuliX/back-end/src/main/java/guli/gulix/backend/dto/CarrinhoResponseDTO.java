package guli.gulix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoResponseDTO {
    private List<ItemCarrinhoResponseDTO> itens;
    private BigDecimal total;
}
