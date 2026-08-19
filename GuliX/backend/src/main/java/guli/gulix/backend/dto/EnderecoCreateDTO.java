package guli.gulix.backend.dto;

import guli.gulix.backend.entity.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCreateDTO {

    @NotBlank(message = "A rua é obrigatória")
    @Size(max = 150, message = "A rua deve possuir no máximo 150 caracteres")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    @Size(max = 20, message = "O número deve possuir no máximo 20 caracteres")
    private String numero;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100, message = "A cidade deve possuir no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 2, message = "O estado deve possuir 2 caracteres")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(
            regexp = "\\d{5}-?\\d{3}",
            message = "O CEP deve estar no formato 00000-000 ou 00000000"
    )
    private String cep;

}
