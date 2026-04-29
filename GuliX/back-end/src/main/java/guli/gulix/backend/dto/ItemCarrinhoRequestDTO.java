package guli.gulix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinhoRequestDTO {
    private Integer produtoId;
    private Integer quantidade;
}
