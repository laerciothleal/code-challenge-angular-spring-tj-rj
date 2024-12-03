package com.backend.service;

import com.backend.exception.ObjectNotFoundException;
import com.backend.model.*;
import com.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroAutorRepository livroAutorRepository;

    @Mock
    private LivroAssuntoRepository livroAssuntoRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AssuntoRepository assuntoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldSaveLivroAndClearRelations() {
        Livro livro = new Livro();
        livro.setCodL(1);

        when(livroRepository.save(livro)).thenReturn(livro);

        Livro result = livroService.saveLivroAndClearRelations(livro);

        assertNotNull(result);
        verify(livroRepository, times(1)).save(livro);
        verify(livroAutorRepository, times(1)).deleteByLivroCodL(livro.getCodL());
        verify(livroAssuntoRepository, times(1)).deleteByLivroCodL(livro.getCodL());
    }

    @Test
    void shouldSaveLivroAutores() {
        Livro livro = new Livro();
        livro.setCodL(1);
        Autor autor = new Autor(1, "Autor Teste");

        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        livroService.saveLivroAutores(livro, List.of(1));

        verify(autorRepository, times(1)).findById(1);
        verify(livroAutorRepository, times(1)).save(any(LivroAutor.class));
    }

    @Test
    void shouldSaveLivroAssuntos() {
        Livro livro = new Livro();
        livro.setCodL(1);
        Assunto assunto = new Assunto(1, "Assunto Teste");

        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));

        livroService.saveLivroAssuntos(livro, List.of(1));

        verify(assuntoRepository, times(1)).findById(1);
        verify(livroAssuntoRepository, times(1)).save(any(LivroAssunto.class));
    }

    @Test
    void shouldThrowExceptionWhenAutorNotFound() {
        Livro livro = new Livro();
        livro.setCodL(1);

        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            livroService.saveLivroAutores(livro, List.of(1));
        });

        assertEquals("Autor não encontrado: 1", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAssuntoNotFound() {
        Livro livro = new Livro();
        livro.setCodL(1);

        when(assuntoRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            livroService.saveLivroAssuntos(livro, List.of(1));
        });

        assertEquals("Assunto não encontrado: 1", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenLivroNotFoundForUpdate() {
        Livro livro = new Livro();
        livro.setCodL(1);

        when(livroRepository.findById(1)).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            livroService.update(1, null);
        });

        assertEquals("Livro não encontrado com id 1", exception.getMessage());
    }

    @Test
    void shouldDeleteLivroById() {
        when(livroRepository.existsById(1)).thenReturn(true);

        livroService.deleteById(1);

        verify(livroRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldThrowExceptionWhenLivroNotFoundForDeletion() {
        when(livroRepository.existsById(1)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            livroService.deleteById(1);
        });

        assertEquals("Livro não encontrado para exclusão com Id: 1", exception.getMessage());
    }
}
