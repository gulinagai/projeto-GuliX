package guli.gulix.backend.dto;
import java.math.BigDecimal;

public record CidadeResponseDTO(

        Integer id,
        String nome,
        Integer estadoId,
        BigDecimal latitudeCentral,
        BigDecimal longitudeCentral

) {}
