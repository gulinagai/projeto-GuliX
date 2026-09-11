package guli.gulix.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "politicas_frete")
@Getter
@Setter
@NoArgsConstructor
public class PoliticaFrete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorBase;

    @Column(nullable = false)
    private Boolean ativo;
}