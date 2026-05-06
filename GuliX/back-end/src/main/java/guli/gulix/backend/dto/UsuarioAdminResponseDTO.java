package guli.gulix.backend.dto;

import guli.gulix.backend.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAdminResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private Role role;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

}
