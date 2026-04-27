package guli.gulix.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
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

    @Column(name = "imagem_url")
    private String imagemURL;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

//  @Column(name = "categoria_id")  // não se coloca isso porque, com essa anotação, faz o hibernate achar que é um tipo simples, e não, porque categoria é uma outra entidade
    @ManyToOne   // indica que existe um relacionamento de muitos produtos para uma categoria
    @JoinColumn(name = "categoria_id")  // indica que esse atributo é um campo FK do banco
    private Categoria categoria;

    //  @Column(name = "marca_id")  // não se coloca isso porque, com essa anotação, faz o hibernate achar que é um tipo simples, e não, porque marca é uma outra entidade
    @ManyToOne   // indica que existe um relacionamento de muitos produtos para uma marca
    @JoinColumn(name = "marca_id")  // indica que esse atributo é um campo FK do banco
    private Marca marca;

    private Boolean destaque;
    private BigDecimal desconto;
}
