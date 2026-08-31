package guli.gulix.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoUpdateDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100,
            message = "O nome deve possuir entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O resumo é obrigatório")
    @Size(min = 3, max = 255,
            message = "O resumo deve possuir entre 3 e 255 caracteres")
    private String resumo;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "A categoria é obrigatória")
    private Integer categoriaId;

    @NotBlank(message = "A URL da imagem é obrigatória.")
    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*\\.jpg$",
            message = "A URL da imagem deve seguir o formato: palavras-separadas-por-hifen.jpg"
    )
    private String imagemURL;

    @NotNull(message = "A marca é obrigatória")
    private Integer marcaId;

    @NotNull(message = "O destaque é obrigatório")
    private Boolean destaque;

    @DecimalMin(value = "0.0", message = "O desconto não pode ser negativo")
    private BigDecimal desconto;

}
