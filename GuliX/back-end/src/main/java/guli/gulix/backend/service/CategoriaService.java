package guli.gulix.backend.service;

import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.dto.CategoriaResponseDTO;
import guli.gulix.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> getListCategoria();

    CategoriaResponseDTO getCategoriaById(Integer categoriaId);

    CategoriaResponseDTO createNewCategoria(CategoriaRequestDTO categoriaRequest);

    CategoriaResponseDTO updateCategoriaById(Integer categoriaId, CategoriaRequestDTO categoriaAtualizar);

    void deleteCategoriaById(Integer categoriaId);
}
