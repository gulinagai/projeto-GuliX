package guli.gulix.backend.controller;

import guli.gulix.backend.config.SecurityConfig;
import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.fixture.ProdutoFixture;
import guli.gulix.backend.security.JwtFilter;
import guli.gulix.backend.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = ProdutoController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        SecurityConfig.class,
                        JwtFilter.class
                }

        ))
@Import(ProdutoControllerSecurityTest.TestSecurityConfig.class)
public class ProdutoControllerSecurityTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockmvc;

    @MockitoBean
    private ProdutoService produtoService;


    // createNewProduto(@Valid @RequestBody ProdutoCreateDTO produtoRequest)

    @Test
    void deveRetornar401QuandoUsuarioNaoAutenticadoAoCriarProduto() throws Exception {

        // Arrange

        ProdutoCreateDTO produtoCriar = ProdutoFixture.produtoCreateDTO();

        // Act

        mockmvc.perform(
                post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoCriar))
        )

        // Assert

                .andExpect(status().isUnauthorized());

        verifyNoInteractions(produtoService);
    }

    @Test
    void deveRetornar403QuandoUsuarioNaoAutorizadoAoCriarProduto() throws Exception {

        // Arrange

        ProdutoCreateDTO produtoCriar = ProdutoFixture.produtoCreateDTO();

        // Act

        mockmvc.perform(
                        post("/api/v1/produtos")
                                .with(user("usuarioTeste").roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produtoCriar))
                )

                // Assert

                .andExpect(status().isForbidden());

        verifyNoInteractions(produtoService);
    }

    // deleteProdutoById(@PathVariable("produtoId")

    @Test
    void deveRetornar401QuandoUsuarioNaoAutenticadoAoDeletarProduto() throws Exception {

        // Arrange

        // Não necessário

        // Act

        mockmvc.perform(
                delete("/api/v1/produtos/1")


        )

        // Assert

                .andExpect(status().isUnauthorized());

        verifyNoInteractions(produtoService);

    }



    @Test
    void deveRetornar403QuandoUsuarioNaoAutorizadoAoDeletarProduto() throws Exception {

        // Arrange

        // Não necessário

        // Act

        mockmvc.perform(
                delete("/api/v1/produtos/1")
                        .with(user("usuarioTeste").roles("USER"))
        )

        // Assert

                .andExpect(status().isForbidden());

        verifyNoInteractions(produtoService);
    }


    // updateProdutoById(@PathVariable("produtoId")

    @Test
    void deveRetornar401QuandoUsuarioNaoAutenticadoAoAtualizarCompletamenteProduto() throws Exception{

        // Arrange

        ProdutoUpdateDTO produtoAtualizar = ProdutoFixture.produtoUpdateDTO();

        // Act

        mockmvc.perform(
                put("/api/v1/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoAtualizar))
        )

        // Assert

                .andExpect(status().isUnauthorized());

        verifyNoInteractions(produtoService);
    }


    @Test
    void deveRetornar403QuandoUsuarioNaoAutorizadoAoAtualizarCompletamenteProduto() throws Exception {

        // Arrange

        ProdutoUpdateDTO produtoAtualizar = ProdutoFixture.produtoUpdateDTO();

        // Act

        mockmvc.perform(
                        put("/api/v1/produtos/1")
                                .with(user("usuarioTeste").roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produtoAtualizar))
                )

        // Assert

                .andExpect(status().isForbidden());

        verifyNoInteractions(produtoService);
    }



    // updatePartialProdutoById(@PathVariable("produtoId")

    @Test
    void deveRetornar401QuandoUsuarioNaoAutenticadoAoAtualizarParcialProduto() throws Exception {

        // Arrange

        ProdutoUpdateDTO produtoAtualizar = ProdutoFixture.produtoUpdateDTO();

        // Act

        mockmvc.perform(
                        patch("/api/v1/produtos/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produtoAtualizar))
                )

        // Assert

                .andExpect(status().isUnauthorized());

        verifyNoInteractions(produtoService);
    }


    @Test
    void deveRetornar403QuandoUsuarioNaoAutorizadoAoAtualizarParcialProduto() throws Exception {

        // Arrange

        ProdutoUpdateDTO produtoAtualizar = ProdutoFixture.produtoUpdateDTO();

        // Act

        mockmvc.perform(
                        patch("/api/v1/produtos/1")
                                .with(user("usuarioTeste").roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(produtoAtualizar))
                )

        // Assert

                .andExpect(status().isForbidden());

        verifyNoInteractions(produtoService);

    }





    @TestConfiguration
    @EnableWebSecurity
    @EnableMethodSecurity
    static class TestSecurityConfig {

        @Bean
        SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(
                                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                            )
                    )
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().authenticated()
                    )
                    .build();
        }
    }

}



