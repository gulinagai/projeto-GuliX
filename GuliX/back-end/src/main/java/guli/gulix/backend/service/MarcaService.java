package guli.gulix.backend.service;

import guli.gulix.backend.dto.MarcaRequestDTO;
import guli.gulix.backend.dto.MarcaResponseDTO;
import guli.gulix.backend.entity.Marca;


import java.util.List;

public interface MarcaService {

    List<MarcaResponseDTO> getListMarca();

    MarcaResponseDTO getMarcaById(Integer marcaId);

    MarcaResponseDTO createNewMarca(MarcaRequestDTO marcaRequest);

    MarcaResponseDTO updateMarcaById(Integer marcaId, MarcaRequestDTO marcaAtualizar);

    void deleteMarcaById(Integer marcaId);
}
