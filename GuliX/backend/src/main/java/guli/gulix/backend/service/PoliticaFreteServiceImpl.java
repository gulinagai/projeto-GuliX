package guli.gulix.backend.service;

import guli.gulix.backend.dto.*;
import guli.gulix.backend.entity.PoliticaFrete;
import guli.gulix.backend.entity.PoliticaFrete;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.PoliticaFreteMapper;
import guli.gulix.backend.repository.PoliticaFreteRepository;
import guli.gulix.backend.repository.PoliticaFreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PoliticaFreteServiceImpl implements PoliticaFreteService {

    private final PoliticaFreteRepository politicaFreteRepository;
    private final PoliticaFreteMapper politicaFreteMapper;

    @Override
    public List<PoliticaFreteResponseDTO> getAllPoliticaFrete() {
        return politicaFreteRepository.findAll().stream().map(
                politicaFreteMapper::toDTO
        ).toList();
    }

    @Override
    public PoliticaFreteResponseDTO getPoliticaFreteById(Integer politicaFreteId) {

        PoliticaFrete politicaFrete = politicaFreteRepository.findById(politicaFreteId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "PoliticaFrete com id " + politicaFreteId + " não encontrado"
                        )
        );

        return politicaFreteMapper.toDTO(politicaFrete);
    }

    @Override
    public PoliticaFreteResponseDTO createNewPoliticaFrete(PoliticaFreteCreateDTO dto) {

        PoliticaFrete politicaFrete = politicaFreteMapper.toEntity(dto);



        return politicaFreteMapper.toDTO(politicaFreteRepository.save(politicaFrete));
    }

    @Override
    public PoliticaFreteResponseDTO updatePoliticaFreteById(Integer politicaFreteId, PoliticaFreteUpdateDTO dto) {

        PoliticaFrete politicaFretePersistida = politicaFreteRepository.findById(politicaFreteId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "PoliticaFrete com id " + politicaFreteId + " não encontrado"
                        )
        );

        politicaFreteMapper.updateFromDto(dto, politicaFretePersistida);

        return politicaFreteMapper.toDTO(politicaFretePersistida);
    }

    @Override
    public void deletePoliticaFreteById(Integer politicaFreteId) {

        PoliticaFrete politicaFrete = politicaFreteRepository.findById(politicaFreteId).orElseThrow(
                ()->
                        new RecursoNaoEncontradoException(
                                "PoliticaFrete com id " + politicaFreteId + " não encontrado"
                        )
        );

        politicaFreteRepository.delete(politicaFrete);

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
