package guli.gulix.backend.service;

import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.entity.Categoria;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.CategoriaMapper;
import guli.gulix.backend.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<Categoria> getListCategoria() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria getCategoriaById(Integer categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                         "Produto com id" + categoriaId + " não encontrado"
                        ));
    }

    @Override
    public Categoria createNewCategoria(CategoriaRequestDTO categoriaRequest) {

        Categoria categoria = categoriaMapper.toEntity(categoriaRequest);

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria updateCategoriaById(Integer categoriaId, CategoriaRequestDTO categoriaAtualizar) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id" + categoriaId + " não encontrado"
                        ));

        categoriaMapper.updateFromDto(categoriaAtualizar, categoria);

        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteCategoriaById(Integer categoriaId) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Produto com id" + categoriaId + " não encontrado"
                        ));

        categoriaRepository.delete(categoria);
    }
}
