package guli.gulix.backend.controller;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.fixture.ProdutoFixture;
import guli.gulix.backend.repository.UsuarioRepository;
import guli.gulix.backend.security.JwtUtil;
import guli.gulix.backend.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class) // sobe um contexto de teste focado na camada MVC
class ProdutoControllerTest {

// para esse teste, será considerado:

//    Spring MVC real;
//    ProdutoController real;
//    ProdutoService mock;

// O foco deste teste não é validar autenticação ou autorização.
//
// Foco:
// - binding de @RequestBody e @PathVariable
// - validações
// - status HTTP
// - headers
// - serialização JSON
// - interação com o Service, que é mockado
//
// Nos endpoints protegidos, @WithMockUser é usado apenas
// para fornecer uma autenticação que permita alcançar o Controller.

//    status().isOk()          // 200 OK
//    status().isCreated()     // 201 Created
//    status().isNoContent()   // 204 No Content
//    status().isBadRequest()  // 400 Bad Request
//    status().isNotFound()    // 404 Not Found


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean // cria um mock e o registra no ApplicationContext do Spring
    private ProdutoService produtoService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private UsuarioRepository usuarioRepository;


    // getAllProduto()

    @Test
    void deveRetornar200AoBuscarTodosOsProdutos() throws Exception {

        // Arrange

        ProdutoResponseDTO produtoEsperado = ProdutoFixture.produtoResponseDTO();

        List<ProdutoResponseDTO> listaProdutos = List.of(
                produtoEsperado
        );

        when(produtoService.getAllProduto()).thenReturn(listaProdutos);

        // Act

        mockMvc.perform(get("/api/v1/produtos"))

        // Assert

                .andExpect(status().isOk())   // status 200 OK
                .andExpect(jsonPath("$[0].id").value(produtoEsperado.getId()))
                .andExpect(jsonPath("$[0].nome").value(produtoEsperado.getNome()))
                .andExpect(jsonPath("$[0].resumo").value(produtoEsperado.getResumo()))
                .andExpect(jsonPath("$[0].preco").value(produtoEsperado.getPreco().doubleValue()))
                .andExpect(jsonPath("$[0].estoque").value(produtoEsperado.getEstoque()))
                .andExpect(jsonPath("$[0].imagemURL").value(produtoEsperado.getImagemURL()))
                .andExpect(jsonPath("$[0].categoriaId").value(produtoEsperado.getCategoriaId()))
                .andExpect(jsonPath("$[0].marcaId").value(produtoEsperado.getMarcaId()))
                .andExpect(jsonPath("$[0].destaque").value(produtoEsperado.getDestaque()))
                .andExpect(jsonPath("$[0].desconto").value(produtoEsperado.getDesconto().doubleValue()));


        verify(produtoService).getAllProduto();
    }


    @Test
    void deveRetornar200AoBuscarTodosOsProdutosQuandoListaVazia() throws Exception {

        // Arrange

        List<ProdutoResponseDTO> listaVazia = List.of();

        when(produtoService.getAllProduto()).thenReturn(listaVazia);

        // Act

        mockMvc.perform(get("/api/v1/produtos"))

        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());


        verify(produtoService).getAllProduto();
    }


    // getProdutoById(@PathVariable("produtoId") Integer produtoId)

    @Test
    void deveRetornar200AoBuscarProdutoPorId() throws Exception {

        // Arrange

        ProdutoResponseDTO produtoEsperado = ProdutoFixture.produtoResponseDTO();

        when(produtoService.getProdutoById(1)).thenReturn(produtoEsperado);

        // Act

        mockMvc.perform(get("/api/v1/produtos/1"))

        // Assert

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produtoEsperado.getId()))
                .andExpect(jsonPath("$.nome").value(produtoEsperado.getNome()))
                .andExpect(jsonPath("$.resumo").value(produtoEsperado.getResumo()))
                .andExpect(jsonPath("$.preco").value(produtoEsperado.getPreco().doubleValue()))
                .andExpect(jsonPath("$.estoque").value(produtoEsperado.getEstoque()))
                .andExpect(jsonPath("$.imagemURL").value(produtoEsperado.getImagemURL()))
                .andExpect(jsonPath("$.categoriaId").value(produtoEsperado.getCategoriaId()))
                .andExpect(jsonPath("$.marcaId").value(produtoEsperado.getMarcaId()))
                .andExpect(jsonPath("$.destaque").value(produtoEsperado.getDestaque()))
                .andExpect(jsonPath("$.desconto").value(produtoEsperado.getDesconto().doubleValue()));


        verify(produtoService).getProdutoById(1);
    }

    @Test
    void deveRetornar404AoBuscarProdutoInexistente() throws Exception{

        // Arrange

        when(produtoService.getProdutoById(0)).thenThrow(new RecursoNaoEncontradoException("Produto com id 0 não encontrado"));

        // Act

        mockMvc.perform(get("/api/v1/produtos/0"))

        // Assert

                .andExpect(status().isNotFound());


        verify(produtoService).getProdutoById(0);
    }

    // createNewProduto(@RequestBody ProdutoCreateDTO produtoRequest)


    @Test
    void deveRetornar201QuandoProdutoCriado() throws Exception {


        // Arrange

        ProdutoCreateDTO produtoACriar = ProdutoFixture.produtoCreateDTO();

        ProdutoResponseDTO produtoEsperado = ProdutoFixture.produtoResponseDTO();


        when(produtoService.createNewProduto(any(ProdutoCreateDTO.class))).thenReturn(produtoEsperado);

        // Act

        mockMvc.perform(post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)    // define o Content-Type da requisição como: application/json
                        .content(objectMapper.writeValueAsString(produtoACriar))) // usa o produtoCreateDTO e converte ele para json para passar na requisição.


        // Assert

                .andExpect(status().isCreated())
                .andExpect(header().string(
                        "Location",
                        "/api/v1/produtos/" + produtoEsperado.getId()
                ))
                .andExpect(jsonPath("$.id").value(produtoEsperado.getId()))
                .andExpect(jsonPath("$.nome").value(produtoEsperado.getNome()))
                .andExpect(jsonPath("$.resumo").value(produtoEsperado.getResumo()))
                .andExpect(jsonPath("$.preco").value(produtoEsperado.getPreco().doubleValue()))
                .andExpect(jsonPath("$.estoque").value(produtoEsperado.getEstoque()))
                .andExpect(jsonPath("$.imagemURL").value(produtoEsperado.getImagemURL()))
                .andExpect(jsonPath("$.categoriaId").value(produtoEsperado.getCategoriaId()))
                .andExpect(jsonPath("$.marcaId").value(produtoEsperado.getMarcaId()))
                .andExpect(jsonPath("$.destaque").value(produtoEsperado.getDestaque()))
                .andExpect(jsonPath("$.desconto").value(produtoEsperado.getDesconto().doubleValue()));


        verify(produtoService).createNewProduto(any(ProdutoCreateDTO.class));
    }

    @Test
    void deveRetornar400QuandoDTOInvalido() throws Exception {


        // Arrange

        ProdutoCreateDTO produtoACriar = ProdutoFixture.produtoCreateDTO();
        produtoACriar.setNome("");

        // Act

        mockMvc.perform(post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoACriar))) // usa o produtoCreateDTO e converte ele para json para passar na requisição.


                // Assert

                .andExpect(status().isBadRequest());

        verifyNoInteractions(produtoService);

    }



    // deleteProdutoById(@PathVariable("produtoId")

    @Test
    void deveRetornar204AoDeletarProdutoPorId() throws Exception {

        // Arrange

            // Não necessário neste teste.

        // Act

        mockMvc.perform(
                delete("/api/v1/produtos/1")
        )

        // Assert

                .andExpect(status().isNoContent());


        verify(produtoService).deleteProdutoById(1);
    }

    @Test
    void deveRetornar404AoDeletarProdutoInexistente() throws Exception {

        // Arrange

        doThrow(new RecursoNaoEncontradoException("Produto com id 0 não encontrado"))
                .when(produtoService).deleteProdutoById(0);

        // Act

        mockMvc.perform(
                delete("/api/v1/produtos/0")
        )

        // Assert

                .andExpect(status().isNotFound());

        verify(produtoService).deleteProdutoById(0);

    }


    // updateProdutoById(@PathVariable("produtoId")

//    @Test
//    void deveRetornar200AoAtualizarCompletamenteProdutoPorId() {
//
//        // Arrange
//
//        ProdutoResponseDTO produtoEsperado = ProdutoFixture.produtoResponseDTO();
//        ProdutoUpdateDTO produtoAtualizar = ProdutoFixture.produtoUpdateDTO();
//
//
//        when(produtoService.updateProdutoById(1, produtoAtualizar)).thenReturn(produtoEsperado);
//
//        // Act
//
//
//        // Assert

    }

}

