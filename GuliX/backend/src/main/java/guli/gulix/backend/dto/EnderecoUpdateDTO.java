package guli.gulix.backend.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDTO {

    @Size(max = 150, message = "A rua deve possuir no máximo 150 caracteres")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A rua não pode estar vazia"
    )
    private String rua;

    @Size(max = 20, message = "O número deve possuir no máximo 20 caracteres")
    @Pattern(
            regexp = ".*\\S.*",
            message = "O número não pode estar vazio"
    )
    private String numero;

    @Size(max = 100, message = "A cidade deve possuir no máximo 100 caracteres")
    @Pattern(
            regexp = ".*\\S.*",
            message = "A cidade não pode estar vazia"
    )
    private String cidade;

    @Size(min = 2, max = 2, message = "O estado deve possuir 2 caracteres")
    @Pattern(
            regexp = ".*\\S.*",
            message = "O estado não pode estar vazio"
    )
    private String estado;

    @Pattern(
            regexp = "\\d{5}-?\\d{3}",
            message = "O CEP deve estar no formato 00000-000 ou 00000000"
    )
    private String cep;

}
