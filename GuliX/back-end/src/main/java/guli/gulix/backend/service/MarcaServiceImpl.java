package guli.gulix.backend.service;

import guli.gulix.backend.dto.MarcaRequestDTO;

import guli.gulix.backend.entity.Marca;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.MarcaMapper;
import guli.gulix.backend.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Override
    public List<Marca> getListMarca() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca getMarcaById(Integer marcaId) {
        return marcaRepository.findById(marcaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id" + marcaId + " não encontrado"
                        ));
    }

    @Override
    public Marca createNewMarca(MarcaRequestDTO marcaRequest) {

        Marca marca = marcaMapper.toEntity(marcaRequest);

        return marcaRepository.save(marca);
    }

    @Override
    public Marca updateMarcaById(Integer marcaId, MarcaRequestDTO marcaAtualizar) {

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id" + marcaId + " não encontrado"
                        ));

        marcaMapper.updateFromDto(marcaAtualizar, marca);

        return marcaRepository.save(marca);
    }

    @Override
    public void deleteMarcaById(Integer marcaId) {

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id" + marcaId + " não encontrado"
                        ));

        marcaRepository.delete(marca);
    }
}
