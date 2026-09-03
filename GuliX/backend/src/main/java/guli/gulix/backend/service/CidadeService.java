package guli.gulix.backend.service;

import guli.gulix.backend.dto.CidadeCreateDTO;
import guli.gulix.backend.dto.CidadeResponseDTO;
import guli.gulix.backend.dto.CidadeUpdateDTO;

import java.util.List;

public interface CidadeService {

    List<CidadeResponseDTO> getAllCidade();

    CidadeResponseDTO getCidadeById(Integer cidadeId);

    CidadeResponseDTO createNewCidade(CidadeCreateDTO dto);

    CidadeResponseDTO updateCidadeById(Integer cidadeId, CidadeUpdateDTO dto);

    void deleteCidadeById(Integer cidadeId);

}
