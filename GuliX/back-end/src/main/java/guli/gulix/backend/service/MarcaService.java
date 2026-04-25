package guli.gulix.backend.service;

import guli.gulix.backend.dto.MarcaRequestDTO;
import guli.gulix.backend.entity.Marca;


import java.util.List;

public interface MarcaService {

    List<Marca> getListMarca();

    Marca getMarcaById(Integer marcaId);

    Marca createNewMarca(MarcaRequestDTO marcaRequest);

    Marca updateMarcaById(Integer marcaId, MarcaRequestDTO marcaAtualizar);

    void deleteMarcaById(Integer marcaId);
}
