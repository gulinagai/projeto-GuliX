package guli.gulix.backend.service;

import guli.gulix.backend.dto.EstadoCreateDTO;
import guli.gulix.backend.dto.EstadoResponseDTO;
import guli.gulix.backend.dto.EstadoUpdateDTO;

import java.util.List;

public interface EstadoService {
    List<EstadoResponseDTO> getAllEstado();

    EstadoResponseDTO getEstadoById(Integer estadoId);

    EstadoResponseDTO createNewEstado(EstadoCreateDTO dto);

    EstadoResponseDTO updateEstadoById(Integer estadoId, EstadoUpdateDTO dto);

    void deleteEstadoById(Integer estadoId);
}
