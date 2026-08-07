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
public class ProdutoResponseDTO {

    private Integer id;
    private String nome;
    private String resumo;
    private BigDecimal preco;
    private Long estoque;
    private Boolean destaque;
    private String imagemURL;
    private BigDecimal desconto;

    private Integer categoriaId;
    private Integer marcaId;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
