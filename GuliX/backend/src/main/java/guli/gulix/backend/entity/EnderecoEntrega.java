package guli.gulix.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
