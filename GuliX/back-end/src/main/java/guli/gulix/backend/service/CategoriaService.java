package guli.gulix.backend.service;

import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaService {

    List<Categoria> getListCategoria();

    Categoria getCategoriaById(Integer categoriaId);

    Categoria createNewCategoria(CategoriaRequestDTO categoriaRequest);

    Categoria updateCategoriaById(Integer categoriaId, CategoriaRequestDTO categoriaAtualizar);

    void deleteCategoriaById(Integer categoriaId);
}
