package guli.gulix.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;
    private String nome;
    private String resumo;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;
    private Long estoque;
    @Column(name = "categoria_id")
    private Long categoriaId;
    @Column(name = "imagem_url")
    private String imagemURL;
    private LocalDateTime criadoEm;
}
