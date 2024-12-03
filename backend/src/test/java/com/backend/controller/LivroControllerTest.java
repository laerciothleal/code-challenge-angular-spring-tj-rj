package com.backend.controller;

import com.backend.model.Livro;
import com.backend.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LivroControllerTest {

    @InjectMocks
    private LivroController livroController;

    @Mock
    private LivroService livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldGetAllLivros() {
        List<Livro> livros = List.of(new Livro());
        when(livroService.findAll()).thenReturn(livros);

        ResponseEntity<List<Livro>> response = livroController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livros, response.getBody());
        verify(livroService, times(1)).findAll();
    }

    @Test
    void shouldDeleteLivroById() {
        when(livroService.existsById(1)).thenReturn(true);

        ResponseEntity<Void> response = livroController.deleteById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(livroService, times(1)).deleteById(1);
    }
}
