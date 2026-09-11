package guli.gulix.backend.service;

import guli.gulix.backend.dto.PoliticaFreteCreateDTO;
import guli.gulix.backend.dto.PoliticaFreteResponseDTO;
import guli.gulix.backend.dto.PoliticaFreteUpdateDTO;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

public interface PoliticaFreteService {
    List<PoliticaFreteResponseDTO> getAllPoliticaFrete();

    PoliticaFreteResponseDTO getPoliticaFreteById(Integer politicaFreteId);

    PoliticaFreteResponseDTO createNewPoliticaFrete(PoliticaFreteCreateDTO dto);

    PoliticaFreteResponseDTO updatePoliticaFreteById(Integer politicaFreteId, PoliticaFreteUpdateDTO dto);

    void deletePoliticaFreteById(Integer politicaFreteId);

    BigDecimal getValorBasePorSiglaEstado(String siglaEstado);
}
