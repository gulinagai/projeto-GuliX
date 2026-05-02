package guli.gulix.backend.service;

import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;

import java.util.List;

public interface EnderecoService {

    List<EnderecoResponseDTO> getListEnderecos();

    EnderecoResponseDTO getEnderecoById(Integer enderecoId);
    
    EnderecoResponseDTO createNewEndereco(EnderecoCreateDTO enderecoRequest);

    void deleteEnderecoById(Integer enderecoId);

    EnderecoResponseDTO updateEnderecoById(Integer enderecoId, EnderecoUpdateDTO enderecotualizar);

    void updateEnderecoPrincipalById(Integer enderecoId);
}
