package com.backend.controller;

import com.backend.config.GlobalExceptionHandler;
import com.backend.controller.v1.AutorController;
import com.backend.controller.v1.request.CreateAutorRequest;
import com.backend.controller.v1.response.AutorResponse;
import com.backend.exception.AutorNotFoundException;
import com.backend.mapper.AutorMapper;
import com.backend.model.Autor;
import com.backend.service.AutorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutorController.class)
@Import(GlobalExceptionHandler.class)
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AutorService autorService;

    @MockBean
    private AutorMapper autorMapper;

    @Test
    void shouldCreateAutor() throws Exception {
        CreateAutorRequest request = new CreateAutorRequest("Test");
        Autor saved = new Autor(1, "Test");
        AutorResponse response = AutorResponse.builder().codAu(1).nome("Test").build();

        when(autorService.save(request)).thenReturn(saved);
        when(autorMapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/api/v1/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codAu").value(1))
                .andExpect(jsonPath("$.nome").value("Test"));
    }

    @Test
    void shouldRejectBlankName() throws Exception {
        mockMvc.perform(post("/api/v1/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldGetAutorById() throws Exception {
        Autor autor = new Autor(1, "Test");
        AutorResponse response = AutorResponse.builder().codAu(1).nome("Test").build();

        when(autorService.findById(1)).thenReturn(autor);
        when(autorMapper.toResponse(autor)).thenReturn(response);

        mockMvc.perform(get("/api/v1/autores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codAu").value(1));
    }

    @Test
    void shouldReturn404WhenAutorMissing() throws Exception {
        when(autorService.findById(99)).thenThrow(new AutorNotFoundException(99));

        mockMvc.perform(get("/api/v1/autores/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldGetAllAutores() throws Exception {
        Autor autor = new Autor(1, "Test");
        when(autorService.findAll()).thenReturn(List.of(autor));
        when(autorMapper.toResponseList(List.of(autor)))
                .thenReturn(List.of(AutorResponse.builder().codAu(1).nome("Test").build()));

        mockMvc.perform(get("/api/v1/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codAu").value(1));
    }

    @Test
    void shouldUpdateAutor() throws Exception {
        CreateAutorRequest request = new CreateAutorRequest("Novo");
        Autor updated = new Autor(1, "Novo");
        when(autorService.update(1, request)).thenReturn(updated);
        when(autorMapper.toResponse(updated)).thenReturn(AutorResponse.builder().codAu(1).nome("Novo").build());

        mockMvc.perform(patch("/api/v1/autores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo"));
    }

    @Test
    void shouldDeleteAutor() throws Exception {
        mockMvc.perform(delete("/api/v1/autores/1"))
                .andExpect(status().isNoContent());

        verify(autorService).deleteById(1);
    }

    @Test
    void shouldReturn404WhenDeletingMissingAutor() throws Exception {
        doThrow(new AutorNotFoundException(1)).when(autorService).deleteById(1);

        mockMvc.perform(delete("/api/v1/autores/1"))
                .andExpect(status().isNotFound());
    }
}
