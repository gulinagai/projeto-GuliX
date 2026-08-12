package guli.gulix.backend.service;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.fixture.ProdutoFixture;
import guli.gulix.backend.mapper.ProdutoMapper;
import guli.gulix.backend.repository.CategoriaRepository;
import guli.gulix.backend.repository.MarcaRepository;
import guli.gulix.backend.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    private Produto produto;
    private ProdutoCreateDTO produtoCreateDTO;
    private ProdutoUpdateDTO produtoUpdateDTO;
    private ProdutoUpdateDTO produtoUpdatePartialDTO;
    private ProdutoResponseDTO produtoResponseDTO;

    @BeforeEach
    void setup() {
        produto = ProdutoFixture.produto();
        produtoCreateDTO = ProdutoFixture.produtoCreateDTO();
        produtoUpdateDTO = ProdutoFixture.produtoUpdateDTO();
        produtoResponseDTO = ProdutoFixture.produtoResponseDTO();
        produtoUpdatePartialDTO = ProdutoFixture.produtoUpdatePartialDTO();
    }

    // getAllProduto()

    @Test
    void deveRetornarListaDeProdutos() {
        // Arrange

        when(produtoRepository.findAll()).thenReturn(List.of(produto));
        when(produtoMapper.toDTO(produto)).thenReturn(produtoResponseDTO);


        // Act
        List<ProdutoResponseDTO> resultado = produtoService.getAllProduto();

        // Assert

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produtoResponseDTO, resultado.get(0));


        // Verifica as interações com as dependências
        verify(produtoRepository).findAll();
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void deveRetornarListaDeProdutosVaziaQuandoNaoExistiremProdutos() {

        // Arrange

        when(produtoRepository.findAll()).thenReturn(List.of());

        // Act

        List<ProdutoResponseDTO> resultado = produtoService.getAllProduto();

        // Assert

        assertNotNull(resultado);
        assertEquals(0, resultado.size());

        verify(produtoRepository).findAll();
        verifyNoInteractions(produtoMapper);

    }

    // getProdutoById()

    @Test
    void deveRetornarProdutoQuandoIdExistir() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        when(produtoMapper.toDTO(produto)).thenReturn(produtoResponseDTO);

        // Act

        ProdutoResponseDTO resultado = produtoService.getProdutoById(1);

        // Assert

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO, resultado);

        verify(produtoRepository).findById(1);
        verify(produtoMapper).toDTO(produto);

    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontradoAoBuscarPorId() {


        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.getProdutoById(1)
        );

        assertEquals("Produto com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verifyNoInteractions(produtoMapper);

    }

    // createNewProduto()

    @Test
    void deveRetornarProdutoNovo() {

        // Arrange

        when(produtoMapper.toEntity(produtoCreateDTO)).thenReturn(produto);

        when(categoriaRepository.findById(produtoCreateDTO.getCategoriaId())).thenReturn(Optional.of(produto.getCategoria()));
        when(marcaRepository.findById(produtoCreateDTO.getMarcaId())).thenReturn(Optional.of(produto.getMarca()));
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(produtoMapper.toDTO(produto)).thenReturn(produtoResponseDTO);

        // Act

        ProdutoResponseDTO resultado = produtoService.createNewProduto(produtoCreateDTO);

        // Assert

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO, resultado);

        verify(produtoMapper).toEntity(produtoCreateDTO);
        verify(categoriaRepository).findById(produtoCreateDTO.getCategoriaId());
        verify(marcaRepository).findById(produtoCreateDTO.getMarcaId());
        verify(produtoRepository).save(produto);
        verify(produtoMapper).toDTO(produto);

    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaAoCriarProdutoNovo() {

        // Arrange

        when(produtoMapper.toEntity(produtoCreateDTO)).thenReturn(produto);
        when(categoriaRepository.findById(produtoCreateDTO.getCategoriaId())).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.createNewProduto(produtoCreateDTO)
        );

        assertEquals("Categoria com id 1 não encontrado", exception.getMessage());

        verify(produtoMapper).toEntity(produtoCreateDTO);
        verify(categoriaRepository).findById(produtoCreateDTO.getCategoriaId());

        verifyNoInteractions(marcaRepository);
        verifyNoInteractions(produtoRepository);

    }

    @Test
    void deveLancarExcecaoQuandoMarcaNaoEncontradaAoCriarProdutoNovo() {


        // Arrange

        when(produtoMapper.toEntity(produtoCreateDTO)).thenReturn(produto);
        when(categoriaRepository.findById(produtoCreateDTO.getCategoriaId())).thenReturn(Optional.of(produto.getCategoria()));
        when(marcaRepository.findById(produtoCreateDTO.getMarcaId())).thenReturn(Optional.empty());


        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.createNewProduto(produtoCreateDTO)
        );

        assertEquals("Marca com id 1 não encontrado", exception.getMessage());

        verify(produtoMapper).toEntity(produtoCreateDTO);
        verify(categoriaRepository).findById(produtoCreateDTO.getCategoriaId());
        verify(marcaRepository).findById(produtoCreateDTO.getMarcaId());

        verifyNoInteractions(produtoRepository);
    }

    // deleteProdutoById()

    @Test
    void deveExcluirProdutoQuandoIdExistir() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        // Act

        produtoService.deleteProdutoById(1);

        // Assert

        verify(produtoRepository).findById(1);
        verify(produtoRepository).delete(produto);

    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontradoAoExcluir() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.deleteProdutoById(1)
        );

        assertEquals("Produto com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verify(produtoRepository, never()).delete(any());   // verifica se esse metodo delete() nunca é chamado pelo produtoRepository independente do que é passado de argumento de delete().
    }

    // updateProdutoById()

    @Test
    void deveRetornarProdutoAtualizado() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(produtoUpdateDTO.getCategoriaId())).thenReturn(Optional.of(produto.getCategoria()));
        when(marcaRepository.findById(produtoUpdateDTO.getMarcaId())).thenReturn(Optional.of(produto.getMarca()));
        when(produtoMapper.toDTO(produto)).thenReturn(produtoResponseDTO);

        // Act

        ProdutoResponseDTO resultado = produtoService.updateProdutoById(1, produtoUpdateDTO);

        // Assert

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO, resultado);

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdateDTO, produto);
        verify(categoriaRepository).findById(produtoUpdateDTO.getCategoriaId());
        verify(marcaRepository).findById(produtoUpdateDTO.getMarcaId());
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontradoAoAtualizar() {

        // Arrange

       when(produtoRepository.findById(1)).thenReturn(Optional.empty());

       // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.updateProdutoById(1, produtoUpdateDTO)
        );

        assertEquals("Produto com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);

        verifyNoInteractions(produtoMapper);
        verifyNoInteractions(categoriaRepository);
        verifyNoInteractions(marcaRepository);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaAoAtualizar() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(produtoUpdateDTO.getCategoriaId())).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.updateProdutoById(1, produtoUpdateDTO)
        );

        assertEquals("Categoria com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdateDTO, produto);
        verify(categoriaRepository).findById(produtoUpdateDTO.getCategoriaId());

        verifyNoInteractions(marcaRepository);
        verify(produtoMapper, never()).toDTO(any());

    }

    @Test
    void deveLancarExcecaoQuandoMarcaNaoEncontradaAoAtualizar() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(produtoUpdateDTO.getCategoriaId())).thenReturn(Optional.of(produto.getCategoria()));
        when(marcaRepository.findById(produtoUpdateDTO.getMarcaId())).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.updateProdutoById(1, produtoUpdateDTO)
        );

        assertEquals("Marca com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdateDTO, produto);
        verify(categoriaRepository).findById(produtoUpdateDTO.getCategoriaId());
        verify(marcaRepository).findById(produtoUpdateDTO.getMarcaId());

        verify(produtoMapper, never()).toDTO(any());
    }

    @Test
    void deveRetornarProdutoAtualizadoParcial() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(produtoUpdatePartialDTO.getCategoriaId())).thenReturn(Optional.of(produto.getCategoria()));
        when(marcaRepository.findById(produtoUpdatePartialDTO.getMarcaId())).thenReturn(Optional.of(produto.getMarca()));
        when(produtoMapper.toDTO(produto)).thenReturn(produtoResponseDTO);

        // Act

        ProdutoResponseDTO resultado = produtoService.updatePartialProdutoById(1, produtoUpdatePartialDTO);

        // Assert

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO, resultado);

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdatePartialDTO, produto);
        verify(categoriaRepository).findById(produtoUpdatePartialDTO.getCategoriaId());
        verify(marcaRepository).findById(produtoUpdatePartialDTO.getMarcaId());
        verify(produtoMapper).toDTO(produto);
    }

    @Test
    void deveRetornarProdutoAtualizadoParcialSemCategoriaEMarca() {

        // Arrange

        produtoUpdatePartialDTO.setCategoriaId(null);
        produtoUpdatePartialDTO.setMarcaId(null);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(produtoMapper.toDTO(produto)).thenReturn(produtoResponseDTO);

        // Act

        ProdutoResponseDTO resultado = produtoService.updatePartialProdutoById(1, produtoUpdatePartialDTO);

        // Assert

        assertNotNull(resultado);
        assertEquals(produtoResponseDTO, resultado);

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdatePartialDTO, produto);
        verify(produtoMapper).toDTO(produto);

        verifyNoInteractions(categoriaRepository);
        verifyNoInteractions(marcaRepository);

    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontradoAoAtualizarParcial() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.updatePartialProdutoById(1, produtoUpdatePartialDTO)
        );

        assertEquals("Produto com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verifyNoInteractions(produtoMapper);
        verifyNoInteractions(categoriaRepository);
        verifyNoInteractions(marcaRepository);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaAoAtualizarParcial() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(produtoUpdatePartialDTO.getCategoriaId())).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.updatePartialProdutoById(1,produtoUpdatePartialDTO)
        );

        assertEquals("Categoria com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdatePartialDTO, produto);
        verify(categoriaRepository).findById(produtoUpdatePartialDTO.getCategoriaId());

        verifyNoInteractions(marcaRepository);
        verify(produtoMapper, never()).toDTO(any());
    }

    @Test
    void deveLancarExcecaoQuandoMarcaNaoEncontradaAoAtualizarParcial() {

        // Arrange

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(produtoUpdatePartialDTO.getCategoriaId())).thenReturn(Optional.of(produto.getCategoria()));
        when(marcaRepository.findById(produtoUpdatePartialDTO.getMarcaId())).thenReturn(Optional.empty());

        // Act + Assert

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                ()-> produtoService.updatePartialProdutoById(1, produtoUpdatePartialDTO)
        );

        assertEquals("Marca com id 1 não encontrado", exception.getMessage());

        verify(produtoRepository).findById(1);
        verify(produtoMapper).updateEntityFromDto(produtoUpdatePartialDTO, produto);
        verify(categoriaRepository).findById(produtoUpdatePartialDTO.getCategoriaId());
        verify(marcaRepository).findById(produtoUpdatePartialDTO.getMarcaId());

        verify(produtoMapper, never()).toDTO(any());

    }
}