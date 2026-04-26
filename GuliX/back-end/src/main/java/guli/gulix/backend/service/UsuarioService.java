package guli.gulix.backend.service;

import guli.gulix.backend.dto.UsuarioCreateDTO;
import guli.gulix.backend.dto.UsuarioResponseDTO;
import guli.gulix.backend.dto.UsuarioUpdateDTO;
import guli.gulix.backend.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> getListUsuario();

    UsuarioResponseDTO getUsuarioById(Integer usuarioId);

    UsuarioResponseDTO createNewUsuario(UsuarioCreateDTO usuarioRequest);

    UsuarioResponseDTO updateUsuarioById(Integer usuarioId, UsuarioUpdateDTO usuarioAtualizar);

    UsuarioResponseDTO updatePatchUsuarioById(Integer usuarioId, UsuarioUpdateDTO usuarioAtualizar);

    void deleteUsuarioById(Integer usuarioId);
}
