package guli.gulix.backend.service;

import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.dto.CategoriaResponseDTO;
import guli.gulix.backend.entity.Categoria;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.CategoriaMapper;
import guli.gulix.backend.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> getListCategoria() {
        return categoriaRepository.findAll().stream().map(categoriaMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO getCategoriaById(Integer categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                         "Categoria com id " + categoriaId + " não encontrado"
                        ));

        return categoriaMapper.toDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO createNewCategoria(CategoriaRequestDTO categoriaRequest) {

        Categoria categoria = categoriaMapper.toEntity(categoriaRequest);

        return categoriaMapper.toDTO(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponseDTO updateCategoriaById(Integer categoriaId, CategoriaRequestDTO categoriaAtualizar) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Categoria com id " + categoriaId + " não encontrado"
                        ));

        categoriaMapper.updateFromDto(categoriaAtualizar, categoria);

        return categoriaMapper.toDTO(categoria);
    }

    @Override
    public void deleteCategoriaById(Integer categoriaId) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Categoria com id " + categoriaId + " não encontrado"
                        ));

        categoriaRepository.delete(categoria);
    }
}
