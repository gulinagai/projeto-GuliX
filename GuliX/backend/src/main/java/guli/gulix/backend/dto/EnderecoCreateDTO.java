package guli.gulix.backend.dto;

import guli.gulix.backend.entity.Usuario;
import jakarta.persistence.*;
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

    private String rua;

    private String numero;

    private String cidade;

    private String estado;

    private String cep;

}
