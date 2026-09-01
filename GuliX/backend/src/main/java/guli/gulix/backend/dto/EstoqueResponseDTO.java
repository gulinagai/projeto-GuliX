package guli.gulix.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueResponseDTO {

    Integer produtoId;

    Integer estoqueTotal;

    Integer estoqueReservado;

    Integer estoqueDisponivel;
}
