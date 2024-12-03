package com.backend.service;

import com.backend.controller.request.AutorRequest;
import com.backend.exception.ObjectNotFoundException;
import com.backend.mappper.AutorMapper;
import com.backend.model.Autor;
import com.backend.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAutor() {
        AutorRequest request = new AutorRequest("Autor Teste");
        Autor mappedAutor = new Autor(1, "Autor Teste");

        when(autorMapper.toEntity(request)).thenReturn(mappedAutor);
        when(autorRepository.save(mappedAutor)).thenReturn(mappedAutor);

        Autor result = autorService.save(request);

        assertNotNull(result);
        assertEquals(mappedAutor, result);
        verify(autorRepository, times(1)).save(mappedAutor);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingAutor() {
        AutorRequest request = new AutorRequest("Autor Teste");
        int id = 1;

        when(autorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> autorService.update(id, request));
    }

    @Test
    void shouldFindAutorById() {
        int id = 1;
        Autor autor = new Autor(1, "Autor Teste");

        when(autorRepository.findById(id)).thenReturn(Optional.of(autor));

        Optional<Autor> result = autorService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(autor, result.get());
    }

    @Test
    void shouldDeleteAutorById() {
        int id = 1;
        doNothing().when(autorRepository).deleteById(id);

        autorService.deleteById(id);

        verify(autorRepository, times(1)).deleteById(id);
    }
}
