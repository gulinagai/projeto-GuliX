package guli.gulix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoUpdateDTO {

    private String nome;
    private String resumo;
    private BigDecimal preco;
    private Long estoque;
    private Integer categoriaId;
    private String imagemURL;
    private Integer marcaId;
    private Boolean destaque;
    private BigDecimal desconto;

}
