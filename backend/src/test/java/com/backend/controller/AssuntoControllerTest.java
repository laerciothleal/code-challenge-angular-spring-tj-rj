package com.backend.controller;

import com.backend.config.GlobalExceptionHandler;
import com.backend.controller.v1.AssuntoController;
import com.backend.controller.v1.request.CreateAssuntoRequest;
import com.backend.controller.v1.response.AssuntoResponse;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.mapper.AssuntoMapper;
import com.backend.model.Assunto;
import com.backend.service.AssuntoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssuntoController.class)
@Import(GlobalExceptionHandler.class)
class AssuntoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssuntoService assuntoService;

    @MockBean
    private AssuntoMapper assuntoMapper;

    @Test
    void shouldCreateAssunto() throws Exception {
        CreateAssuntoRequest request = new CreateAssuntoRequest("Test");
        Assunto saved = new Assunto(1, "Test");
        AssuntoResponse response = AssuntoResponse.builder().codigoAs(1).descricao("Test").build();

        when(assuntoService.save(request)).thenReturn(saved);
        when(assuntoMapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/api/v1/assuntos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoAs").value(1))
                .andExpect(jsonPath("$.descricao").value("Test"));
    }

    @Test
    void shouldRejectBlankDescricao() throws Exception {
        mockMvc.perform(post("/api/v1/assuntos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllAssuntos() throws Exception {
        Assunto assunto = new Assunto(1, "Test");
        when(assuntoService.findAll()).thenReturn(List.of(assunto));
        when(assuntoMapper.toResponseList(List.of(assunto)))
                .thenReturn(List.of(AssuntoResponse.builder().codigoAs(1).descricao("Test").build()));

        mockMvc.perform(get("/api/v1/assuntos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoAs").value(1));
    }

    @Test
    void shouldGetAssuntoById() throws Exception {
        Assunto assunto = new Assunto(1, "Test");
        when(assuntoService.findById(1)).thenReturn(assunto);
        when(assuntoMapper.toResponse(assunto))
                .thenReturn(AssuntoResponse.builder().codigoAs(1).descricao("Test").build());

        mockMvc.perform(get("/api/v1/assuntos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoAs").value(1));
    }

    @Test
    void shouldReturn404WhenAssuntoMissing() throws Exception {
        when(assuntoService.findById(99)).thenThrow(new AssuntoNotFoundException(99));

        mockMvc.perform(get("/api/v1/assuntos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAssunto() throws Exception {
        mockMvc.perform(delete("/api/v1/assuntos/1"))
                .andExpect(status().isNoContent());

        verify(assuntoService).deleteById(1);
    }

    @Test
    void shouldReturn404WhenDeletingMissingAssunto() throws Exception {
        doThrow(new AssuntoNotFoundException(1)).when(assuntoService).deleteById(1);

        mockMvc.perform(delete("/api/v1/assuntos/1"))
                .andExpect(status().isNotFound());
    }
}
