package guli.gulix.backend.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String resumo;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;
    private Long estoque;

    @Column(name = "categoria_id")
    private Integer categoriaId;

    @Column(name = "imagem_url")
    private String imagemURL;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "marca_id")
    private Integer marcaId;
    private Boolean destaque;

    private BigDecimal desconto;
}
