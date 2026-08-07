package guli.gulix.backend.service;

import guli.gulix.backend.dto.MarcaRequestDTO;

import guli.gulix.backend.dto.MarcaResponseDTO;
import guli.gulix.backend.entity.Marca;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.MarcaMapper;
import guli.gulix.backend.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MarcaResponseDTO> getListMarca() {
        return marcaRepository.findAll().stream().map(marcaMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaResponseDTO getMarcaById(Integer marcaId) {
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Marca com id " + marcaId + " não encontrado"
                        ));

        return marcaMapper.toDTO(marca);
    }

    @Override
    public MarcaResponseDTO createNewMarca(MarcaRequestDTO marcaRequest) {

        Marca novaMarca = marcaMapper.toEntity(marcaRequest);

        return marcaMapper.toDTO(marcaRepository.save(novaMarca));
    }

    @Override
    public MarcaResponseDTO updateMarcaById(Integer marcaId, MarcaRequestDTO marcaAtualizar) {

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Marca com id " + marcaId + " não encontrado"
                        ));

        marcaMapper.updateFromDto(marcaAtualizar, marca);

        return marcaMapper.toDTO(marca);
    }

    @Override
    public void deleteMarcaById(Integer marcaId) {

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Marca com id " + marcaId + " não encontrado"
                        ));

        marcaRepository.delete(marca);
    }
}
