package guli.gulix.backend.service;

import guli.gulix.backend.dto.UsuarioCreateDTO;
import guli.gulix.backend.dto.UsuarioResponseDTO;
import guli.gulix.backend.dto.UsuarioUpdateDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.UsuarioMapper;
import guli.gulix.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioResponseDTO> getListUsuario() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).toList();
    }

    @Override
    public UsuarioResponseDTO getUsuarioById(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id" + usuarioId + " não encontrado"
                        ));

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO createNewUsuario(UsuarioCreateDTO usuarioRequest) {

        Usuario novoUsuario = usuarioMapper.toEntity(usuarioRequest);

         return usuarioMapper.toDTO(usuarioRepository.save(novoUsuario));
    }

    @Override
    public UsuarioResponseDTO updateUsuarioById(Integer usuarioId, UsuarioUpdateDTO usuarioAtualizar) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id" + usuarioId + " não encontrado"
                        ));

        usuarioMapper.updateEntityfromDTO(usuarioAtualizar, usuario);

        return usuarioMapper.toDTO(usuarioRepository.save(usuario));

    }

    @Override
    public UsuarioResponseDTO updatePatchUsuarioById(Integer usuarioId, UsuarioUpdateDTO usuarioAtualizar) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id" + usuarioId + " não encontrado"
                        ));

        usuarioMapper.updateEntityfromDTO(usuarioAtualizar, usuario);

        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void deleteUsuarioById(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id" + usuarioId + " não encontrado"
                        ));

        usuarioRepository.delete(usuario);
    }

}
