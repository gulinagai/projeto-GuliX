package guli.gulix.backend.service;

import guli.gulix.backend.dto.UsuarioCreateDTO;
import guli.gulix.backend.dto.UsuarioResponseDTO;
import guli.gulix.backend.dto.UsuarioUpdateDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.entity.enums.Role;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.UsuarioMapper;
import guli.gulix.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> getListUsuario() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
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

        novoUsuario.setSenhaHash(passwordEncoder.encode(usuarioRequest.getSenha()));

        novoUsuario.setRole(Role.ROLE_USER);

        Usuario salvo = usuarioRepository.save(novoUsuario);

         return usuarioMapper.toDTO(salvo);
    }

    @Override
    public UsuarioResponseDTO updateUsuarioById(Integer usuarioId, UsuarioUpdateDTO usuarioAtualizar) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id" + usuarioId + " não encontrado"
                        ));

        usuarioMapper.updateEntityfromDTO(usuarioAtualizar, usuario);

        if(usuarioAtualizar.getSenha() != null && !usuarioAtualizar.getSenha().isBlank()) {
            usuario.setSenhaHash(passwordEncoder.encode(usuarioAtualizar.getSenha()));
        }

        return usuarioMapper.toDTO(usuario);

    }

    @Override
    public UsuarioResponseDTO updatePatchUsuarioById(Integer usuarioId, UsuarioUpdateDTO usuarioAtualizar) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id " + usuarioId + " não encontrado"
                        ));

        usuarioMapper.updateEntityfromDTO(usuarioAtualizar, usuario);

        if(usuarioAtualizar.getSenha() != null && !usuarioAtualizar.getSenha().isBlank()) {
            usuario.setSenhaHash(passwordEncoder.encode(usuarioAtualizar.getSenha()));
        }

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public void deleteUsuarioById(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Usuário com id " + usuarioId + " não encontrado"
                        ));

        usuarioRepository.delete(usuario);
    }

}
