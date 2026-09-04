package guli.gulix.backend.service;

import guli.gulix.backend.dto.CidadeCreateDTO;
import guli.gulix.backend.dto.CidadeResponseDTO;
import guli.gulix.backend.dto.CidadeUpdateDTO;
import guli.gulix.backend.entity.Cidade;
import guli.gulix.backend.entity.Estado;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.CidadeMapper;
import guli.gulix.backend.repository.CidadeRepository;
import guli.gulix.backend.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;
    private final EstadoRepository estadoRepository;

    @Override
    public List<CidadeResponseDTO> getAllCidade() {
        return cidadeRepository.findAll().stream().map(
                item-> {
                    CidadeResponseDTO response = cidadeMapper.toDTO(item);
                    return response;
                }
        ).toList();
    }

    @Override
    public CidadeResponseDTO getCidadeById(Integer cidadeId) {

        Cidade cidade = cidadeRepository.findById(cidadeId).orElseThrow(
                ()->
                new RecursoNaoEncontradoException(
                        "Cidade com id " + cidadeId + " não encontrado"
                )
        );

        return cidadeMapper.toDTO(cidade);
    }

    @Override
    public CidadeResponseDTO createNewCidade(CidadeCreateDTO dto) {

        Cidade cidade = cidadeMapper.toEntity(dto);


        Estado estado = estadoRepository.findById(dto.estadoId()).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "Estado com id " + dto.estadoId() + " não encontrado"
                        )
        );

        cidade.setEstado(estado);



        return cidadeMapper.toDTO(cidadeRepository.save(cidade));
    }

    @Override
    public CidadeResponseDTO updateCidadeById(Integer cidadeId, CidadeUpdateDTO dto) {

        Cidade cidadePersistida = cidadeRepository.findById(cidadeId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "Cidade com id " + cidadeId + " não encontrado"
                        )
        );

        if(dto.estadoId() != null) {
            Estado estado = estadoRepository.findById(dto.estadoId()).orElseThrow(
                    ()->
                            new RecursoNaoEncontradoException(
                                    "Estado com id " + dto.estadoId() + " não encontrado"
                            )
            );

            cidadePersistida.setEstado(estado);
        }

        cidadeMapper.updateFromDto(dto, cidadePersistida);

        return cidadeMapper.toDTO(cidadePersistida);
    }

    @Override
    public void deleteCidadeById(Integer cidadeId) {

        Cidade cidade = cidadeRepository.findById(cidadeId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "Cidade com id " + cidadeId + " não encontrado"
                        )
        );

        cidadeRepository.delete(cidade);

    }
}
