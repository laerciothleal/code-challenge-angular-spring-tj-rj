package com.backend.controller;

import com.backend.controller.request.AutorRequest;
import com.backend.controller.response.AutorResponse;
import com.backend.model.Autor;
import com.backend.service.AutorService;
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

class AutorControllerTest {

    @InjectMocks
    private AutorController autorController;

    @Mock
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAutor() {
        AutorRequest request = new AutorRequest("Test");
        Autor savedAutor = new Autor(1, "Test");

        when(autorService.save(request)).thenReturn(savedAutor);

        ResponseEntity<Autor> response = autorController.save(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedAutor, response.getBody());
        verify(autorService, times(1)).save(request);
    }

    @Test
    void shouldGetAllAutores() {
        List<Autor> autores = List.of(new Autor(1, "Test"));
        when(autorService.findAll()).thenReturn(autores);

        ResponseEntity<List<Autor>> response = autorController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(autores, response.getBody());
        verify(autorService, times(1)).findAll();
    }

    @Test
    void shouldGetAutorById() {
        Autor autor = new Autor(1, "Test");
        when(autorService.findById(1)).thenReturn(Optional.of(autor));

        ResponseEntity<AutorResponse> response = autorController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(autor, response.getBody());
        verify(autorService, times(1)).findById(1);
    }

    @Test
    void shouldDeleteAutorById() {
        when(autorService.existsById(1)).thenReturn(true);

        ResponseEntity<Void> response = autorController.deleteById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(autorService, times(1)).deleteById(1);
    }
}
