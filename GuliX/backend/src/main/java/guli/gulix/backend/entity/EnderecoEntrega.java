package guli.gulix.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoEntrega {
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
