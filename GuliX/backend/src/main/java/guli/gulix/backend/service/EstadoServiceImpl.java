package guli.gulix.backend.service;

import guli.gulix.backend.dto.EstadoCreateDTO;
import guli.gulix.backend.dto.EstadoResponseDTO;
import guli.gulix.backend.dto.EstadoUpdateDTO;
import guli.gulix.backend.entity.Estado;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.EstadoMapper;
import guli.gulix.backend.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    @Override
    public List<EstadoResponseDTO> getAllEstado() {
        return estadoRepository.findAll().stream().map(
                estadoMapper::toDTO
        ).toList();
    }

    @Override
    public EstadoResponseDTO getEstadoById(Integer estadoId) {

        Estado estado = estadoRepository.findById(estadoId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "Estado com id " + estadoId + " não encontrado"
                        )
        );

        return estadoMapper.toDTO(estado);
    }

    @Override
    public EstadoResponseDTO createNewEstado(EstadoCreateDTO dto) {

        Estado estado = estadoMapper.toEntity(dto);



        return estadoMapper.toDTO(estadoRepository.save(estado));
    }

    @Override
    public EstadoResponseDTO updateEstadoById(Integer estadoId, EstadoUpdateDTO dto) {

        Estado estadoPersistida = estadoRepository.findById(estadoId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "Estado com id " + estadoId + " não encontrado"
                        )
        );

        estadoMapper.updateFromDto(dto, estadoPersistida);

        return estadoMapper.toDTO(estadoPersistida);
    }

    @Override
    public void deleteEstadoById(Integer estadoId) {

        Estado estado = estadoRepository.findById(estadoId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "Estado com id " + estadoId + " não encontrado"
                        )
        );

        estadoRepository.delete(estado);

    }
}
