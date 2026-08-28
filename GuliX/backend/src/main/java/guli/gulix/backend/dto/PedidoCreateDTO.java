package guli.gulix.backend.dto;

import guli.gulix.backend.entity.Endereco;
import guli.gulix.backend.entity.enums.MetodoPagamento;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateDTO {

    @NotNull(message = "O endereço é obrigatório")
    @Positive(message = "O ID do endereço deve ser positivo")
    private Integer enderecoId;

    @NotNull(message = "O método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;

    @Positive(message = "O número de parcelas deve ser maior que zero")
    @Max(48)
    private Integer numeroParcelas;
}


