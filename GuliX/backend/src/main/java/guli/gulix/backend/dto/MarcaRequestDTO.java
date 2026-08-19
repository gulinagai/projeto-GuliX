package guli.gulix.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaRequestDTO {
    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(max = 100, message = "O nome da marca deve possuir no máximo 100 caracteres")
    private String nome;
}
