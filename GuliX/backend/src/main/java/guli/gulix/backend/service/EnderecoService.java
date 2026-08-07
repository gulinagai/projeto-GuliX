package guli.gulix.backend.service;

import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;
import guli.gulix.backend.entity.Usuario;

import java.util.List;

public interface EnderecoService {

    List<EnderecoResponseDTO> getListEnderecos(Usuario usuarioLogado);

    EnderecoResponseDTO getEnderecoById(Integer enderecoId, Usuario usuarioLogado);
    
    EnderecoResponseDTO createNewEndereco(EnderecoCreateDTO enderecoRequest, Usuario usuario);

    void deleteEnderecoById(Integer enderecoId, Usuario usuarioLogado);

    EnderecoResponseDTO updateEnderecoById(Integer enderecoId, EnderecoUpdateDTO enderecotualizar,  Usuario usuarioLogado);

    void updateEnderecoPrincipalById(Integer enderecoId, Usuario usuarioLogado);
}
