package guli.gulix.backend.entity;

import guli.gulix.backend.entity.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPedido statusPedido;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false, nullable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "rua", column = @Column(name = "entrega_rua")),
            @AttributeOverride(name = "numero", column = @Column(name = "entrega_numero")),
            @AttributeOverride(name = "cidade", column = @Column(name = "entrega_cidade")),
            @AttributeOverride(name = "estado", column = @Column(name = "entrega_estado")),
            @AttributeOverride(name = "cep", column = @Column(name = "entrega_cep"))
    })
    private EnderecoEntrega enderecoEntrega;

    @PrePersist
    public void prePersist() {
        if (statusPedido == null) {
            statusPedido = StatusPedido.PENDENTE;
        }
    }

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
}
