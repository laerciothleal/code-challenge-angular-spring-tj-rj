package com.backend.controller;

import com.backend.config.GlobalExceptionHandler;
import com.backend.controller.v1.LivroController;
import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.controller.v1.response.LivroResponse;
import com.backend.exception.LivroNotFoundException;
import com.backend.mapper.LivroMapper;
import com.backend.model.Livro;
import com.backend.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroController.class)
@Import(GlobalExceptionHandler.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LivroService livroService;

    @MockBean
    private LivroMapper livroMapper;

    @Test
    void shouldCreateLivro() throws Exception {
        CreateLivroRequest request = request();
        Livro saved = Livro.builder().codL(1).titulo("Clean Code").build();
        LivroResponse response = LivroResponse.builder()
                .codL(1)
                .titulo("Clean Code")
                .autores(List.of())
                .assuntos(List.of())
                .build();

        when(livroService.save(request)).thenReturn(saved);
        when(livroMapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codL").value(1))
                .andExpect(jsonPath("$.titulo").value("Clean Code"));
    }

    @Test
    void shouldGetAllLivros() throws Exception {
        Livro livro = Livro.builder().codL(1).titulo("Clean Code").build();
        when(livroService.findAll()).thenReturn(List.of(livro));
        when(livroMapper.toResponseList(List.of(livro))).thenReturn(List.of(
                LivroResponse.builder().codL(1).titulo("Clean Code").autores(List.of()).assuntos(List.of()).build()
        ));

        mockMvc.perform(get("/api/v1/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codL").value(1));
    }

    @Test
    void shouldReturn404WhenLivroMissing() throws Exception {
        when(livroService.findById(99)).thenThrow(new LivroNotFoundException(99));

        mockMvc.perform(get("/api/v1/livros/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteLivro() throws Exception {
        mockMvc.perform(delete("/api/v1/livros/1"))
                .andExpect(status().isNoContent());

        verify(livroService).deleteById(1);
    }

    @Test
    void shouldReturn404WhenDeletingMissingLivro() throws Exception {
        doThrow(new LivroNotFoundException(1)).when(livroService).deleteById(1);

        mockMvc.perform(delete("/api/v1/livros/1"))
                .andExpect(status().isNotFound());
    }

    private static CreateLivroRequest request() {
        return new CreateLivroRequest(
                "Clean Code",
                "Alta Books",
                1,
                "2008",
                BigDecimal.TEN,
                List.of(1),
                List.of(1)
        );
    }
}
