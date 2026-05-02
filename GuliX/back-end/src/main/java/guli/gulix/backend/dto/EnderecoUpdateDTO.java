package guli.gulix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDTO {

    private String rua;

    private String numero;

    private String cidade;

    private String estado;

    private String cep;

}
