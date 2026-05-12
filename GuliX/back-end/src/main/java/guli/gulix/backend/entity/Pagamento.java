package guli.gulix.backend.entity;

import guli.gulix.backend.entity.enums.MetodoPagamento;
import guli.gulix.backend.entity.enums.StatusPagamento;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPagamento statusPagamento;

    @Column(name = "valor_original",
            nullable = false,
            precision = 10,
            scale = 2)
    private BigDecimal valorOriginal;

    @Column(nullable = false,
            precision = 10,
            scale = 2)
    private BigDecimal desconto;

    @Column(name = "valor_final",
            nullable = false,
            precision = 10,
            scale = 2)
    private BigDecimal valorFinal;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    @Column(name = "valor_parcela",
            precision = 10,
            scale = 2)
    private BigDecimal valorParcela;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        if (statusPagamento == null) {
            statusPagamento = StatusPagamento.PENDENTE;
        }

        if(metodoPagamento != MetodoPagamento.CARTAO_CREDITO) {
            numeroParcelas = null;
            valorParcela = null;
        }
    }
}
