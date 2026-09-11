package guli.gulix.backend.dto;

public record FilialResponseDTO(
        Integer id,
        String nome,
        EnderecoFilialDTO endereco,
        Boolean ativo
) {
}