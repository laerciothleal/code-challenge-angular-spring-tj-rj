package com.backend.service;

import com.backend.controller.v1.request.CreateAutorRequest;
import com.backend.exception.AutorNotFoundException;
import com.backend.mapper.AutorMapper;
import com.backend.model.Autor;
import com.backend.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @Test
    void shouldSaveAutor() {
        CreateAutorRequest request = new CreateAutorRequest("Autor Teste");
        Autor mapped = new Autor(null, "Autor Teste");
        Autor saved = new Autor(1, "Autor Teste");

        when(autorMapper.toEntity(request)).thenReturn(mapped);
        when(autorRepository.save(mapped)).thenReturn(saved);

        Autor result = autorService.save(request);

        assertEquals(1, result.getCodau());
        assertEquals("Autor Teste", result.getNome());
        verify(autorRepository).save(mapped);
    }

    @Test
    void shouldUpdateAutor() {
        CreateAutorRequest request = new CreateAutorRequest("Novo Nome");
        Autor existing = new Autor(1, "Antigo");
        Autor mapped = new Autor(1, "Novo Nome");

        when(autorRepository.findById(1)).thenReturn(Optional.of(existing));
        when(autorMapper.toEntity(request, existing)).thenReturn(mapped);
        when(autorRepository.save(mapped)).thenReturn(mapped);

        Autor result = autorService.update(1, request);

        assertEquals("Novo Nome", result.getNome());
    }

    @Test
    void shouldThrowWhenUpdatingMissingAutor() {
        CreateAutorRequest request = new CreateAutorRequest("Autor Teste");
        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AutorNotFoundException.class, () -> autorService.update(1, request));
    }

    @Test
    void shouldFindAutorById() {
        Autor autor = new Autor(1, "Autor Teste");
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        Autor result = autorService.findById(1);

        assertEquals(autor, result);
    }

    @Test
    void shouldThrowWhenAutorNotFound() {
        when(autorRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(AutorNotFoundException.class, () -> autorService.findById(99));
    }

    @Test
    void shouldFindAllAutores() {
        when(autorRepository.findAll()).thenReturn(List.of(new Autor(1, "A")));

        assertEquals(1, autorService.findAll().size());
    }

    @Test
    void shouldDeleteAutorById() {
        when(autorRepository.existsById(1)).thenReturn(true);
        doNothing().when(autorRepository).deleteById(1);

        autorService.deleteById(1);

        verify(autorRepository).deleteById(1);
    }

    @Test
    void shouldThrowWhenDeletingMissingAutor() {
        when(autorRepository.existsById(1)).thenReturn(false);

        assertThrows(AutorNotFoundException.class, () -> autorService.deleteById(1));
    }
}
