package guli.gulix.backend.service;

import guli.gulix.backend.dto.PoliticaFreteCreateDTO;
import guli.gulix.backend.dto.PoliticaFreteResponseDTO;
import guli.gulix.backend.dto.PoliticaFreteUpdateDTO;
import guli.gulix.backend.entity.PoliticaFrete;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.repository.PoliticaFreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PoliticaFreteServiceImpl implements PoliticaFreteService {

    private final PoliticaFreteRepository politicaFreteRepository;

    @Override
    public List<PoliticaFreteResponseDTO> getAllPoliticaFrete() {
        return List.of();
    }

    @Override
    public PoliticaFreteResponseDTO getPoliticaFreteById(Integer politicaFreteId) {
        return null;
    }

    @Override
    public PoliticaFreteResponseDTO createNewPoliticaFrete(PoliticaFreteCreateDTO dto) {
        return null;
    }

    @Override
    public PoliticaFreteResponseDTO updatePoliticaFreteById(Integer politicaFreteId, PoliticaFreteUpdateDTO dto) {
        return null;
    }

    @Override
    public void deletePoliticaFreteById(Integer politicaFreteId) {

    }

    @Override
    public BigDecimal getValorBasePorSiglaEstado(String siglaEstado) {

        PoliticaFrete politicaFrete = politicaFreteRepository.findByEstadoSigla(siglaEstado)
                .orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Política de frete não encontrada"
                )
        );

        return politicaFrete.getValorBase();

    }

}
