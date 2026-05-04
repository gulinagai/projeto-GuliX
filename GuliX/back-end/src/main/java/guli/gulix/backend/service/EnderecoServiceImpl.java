package guli.gulix.backend.service;

import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;
import guli.gulix.backend.entity.Endereco;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.EnderecoMapper;
import guli.gulix.backend.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@RequiredArgsConstructor
//@Service
//@Transactional
//public class EnderecoServiceImpl implements EnderecoService{
//
//    private final EnderecoRepository enderecoRepository;
//    private final EnderecoMapper enderecoMapper;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<EnderecoResponseDTO> getListEnderecos() {  // passar usuarioId
//            return enderecoRepository.findByUsuarioId().stream().map(enderecoMapper::toDTO).toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public EnderecoResponseDTO getEnderecoById(Integer enderecoId) {
//            Endereco endereco = enderecoRepository.findById(enderecoId)
//                    .orElseThrow(()->
//                            new RecursoNaoEncontradoException(
//                                    "Endereço com id " + enderecoId + " não encontrado"
//                            ));
//
//            return enderecoMapper.toDTO(endereco);
//    }
//
//    @Override
//    public EnderecoResponseDTO createNewEndereco(EnderecoCreateDTO enderecoRequest) { // adicionar Usuario usuarioLogado
//        Endereco endereco = enderecoMapper.toEntity(enderecoRequest);
//
//        // endereco.setUsuario(usuarioLogado)
//
//        // validaPrimeiroEndereco(usuarioLogado.getId(), endereco);
//
//        return enderecoMapper.toDTO(enderecoRepository.save(endereco));
//
//
//        //
//
//
//
//    }
//
//    @Override
//    public void deleteEnderecoById(Integer enderecoId) {
//        Endereco endereco = enderecoRepository.findById(enderecoId)
//                .orElseThrow(()->
//                        new RecursoNaoEncontradoException(
//                                "Endereço com id " + enderecoId + " não encontrado"
//                        ));
//
//        enderecoRepository.delete(endereco);
//    }
//
//    @Override
//    public EnderecoResponseDTO updateEnderecoById(Integer enderecoId, EnderecoUpdateDTO enderecotualizar) {
//        Endereco endereco = enderecoRepository.findById(enderecoId)
//                .orElseThrow(()->
//                        new RecursoNaoEncontradoException(
//                                "Endereço com id " + enderecoId + " não encontrado"
//                        ));
//
//        enderecoMapper.updateFromDto(enderecotualizar, endereco);
//
//        return enderecoMapper.toDTO(endereco);
//    }
//
//    @Override
//    public void updateEnderecoPrincipalById(Integer enderecoId) {
//        Endereco enderecoExiste = enderecoRepository.findById(enderecoId)
//                .orElseThrow(()->
//                        new RecursoNaoEncontradoException(
//                                "Endereço com id " + enderecoId + " não encontrado"
//                        ));
//
//        List<Endereco> listaEnderecos = enderecoRepository.findByUsuarioId(enderecoExiste.getUsuario().getId());
//
//       limpaPrincipal(listaEnderecos);
//
//        enderecoExiste.setPrincipal(true);
//    }
//
//    public void limpaPrincipal(List<Endereco> listaEnderecos) {
//        for (Endereco itemEndereco: listaEnderecos) {
//                itemEndereco.setPrincipal(false);
//        }
//    }

//    private void validaPrimeiroEndereco(Usuario usuarioLogado, Endereco endereco) {
//        List<Endereco> enderecos = enderecoRepository.findByUsuarioId(usuarioId);
//
//        if (enderecos.isEmpty()) {
//            endereco.setPrincipal(true);
//        }
//    }

//}
