package guli.gulix.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCreateDTO {

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
