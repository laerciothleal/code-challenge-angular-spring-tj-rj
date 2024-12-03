package com.backend.controller;

import com.backend.controller.request.AssuntoRequest;
import com.backend.model.Assunto;
import com.backend.service.AssuntoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AssuntoControllerTest {

    @InjectMocks
    private AssuntoController assuntoController;

    @Mock
    private AssuntoService assuntoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAssunto() {
        AssuntoRequest request = new AssuntoRequest("Test");
        Assunto savedAssunto = new Assunto(1, "Test");

        when(assuntoService.save(request)).thenReturn(savedAssunto);

        ResponseEntity<Assunto> response = assuntoController.save(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedAssunto, response.getBody());
        verify(assuntoService, times(1)).save(request);
    }

    @Test
    void shouldGetAllAssuntos() {
        List<Assunto> assuntos = List.of(new Assunto(1, "Test"));
        when(assuntoService.findAll()).thenReturn(assuntos);

        ResponseEntity<List<Assunto>> response = assuntoController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assuntos, response.getBody());
        verify(assuntoService, times(1)).findAll();
    }

    @Test
    void shouldGetAssuntoById() {
        Assunto assunto = new Assunto(1, "Test");
        when(assuntoService.findById(1)).thenReturn(Optional.of(assunto));

        ResponseEntity<Assunto> response = assuntoController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assunto, response.getBody());
        verify(assuntoService, times(1)).findById(1);
    }

    @Test
    void shouldDeleteAssuntoById() {
        when(assuntoService.existsById(1)).thenReturn(true);

        ResponseEntity<Void> response = assuntoController.deleteById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(assuntoService, times(1)).deleteById(1);
    }
}
