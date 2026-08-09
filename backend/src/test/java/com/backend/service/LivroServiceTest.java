package com.backend.service;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.exception.AutorNotFoundException;
import com.backend.exception.LivroNotFoundException;
import com.backend.mapper.LivroMapper;
import com.backend.model.Assunto;
import com.backend.model.Autor;
import com.backend.model.Livro;
import com.backend.model.LivroAssunto;
import com.backend.model.LivroAutor;
import com.backend.repository.AssuntoRepository;
import com.backend.repository.AutorRepository;
import com.backend.repository.LivroAssuntoRepository;
import com.backend.repository.LivroAutorRepository;
import com.backend.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @Mock
    private LivroMapper livroMapper;

    @Test
    void shouldSaveLivroWithRelations() {
        CreateLivroRequest request = request();
        Livro mapped = Livro.builder().titulo("Clean Code").build();
        Livro saved = Livro.builder().codL(1).titulo("Clean Code").build();
        Autor autor = new Autor(1, "Autor Teste");
        Assunto assunto = new Assunto(1, "Assunto Teste");

        when(livroMapper.toEntity(request)).thenReturn(mapped);
        when(livroRepository.save(mapped)).thenReturn(saved);
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));
        when(livroAutorRepository.save(any(LivroAutor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(livroAssuntoRepository.save(any(LivroAssunto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Livro result = livroService.save(request);

        assertEquals(1, result.getCodL());
        assertEquals(1, result.getLivroAutores().size());
        assertEquals(1, result.getLivroAssuntos().size());
        verify(livroAutorRepository).deleteByLivroCodL(1);
        verify(livroAssuntoRepository).deleteByLivroCodL(1);
    }

    @Test
    void shouldThrowWhenAutorNotFoundOnSave() {
        CreateLivroRequest request = request();
        Livro mapped = Livro.builder().titulo("Clean Code").build();
        Livro saved = Livro.builder().codL(1).titulo("Clean Code").build();

        when(livroMapper.toEntity(request)).thenReturn(mapped);
        when(livroRepository.save(mapped)).thenReturn(saved);
        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        AutorNotFoundException exception = assertThrows(AutorNotFoundException.class, () -> livroService.save(request));

        assertEquals("Autor com o id '1' não foi encontrado no sistema.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenAssuntoNotFoundOnSave() {
        CreateLivroRequest request = request();
        Livro mapped = Livro.builder().titulo("Clean Code").build();
        Livro saved = Livro.builder().codL(1).titulo("Clean Code").build();
        Autor autor = new Autor(1, "Autor Teste");

        when(livroMapper.toEntity(request)).thenReturn(mapped);
        when(livroRepository.save(mapped)).thenReturn(saved);
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        when(livroAutorRepository.save(any(LivroAutor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(assuntoRepository.findById(1)).thenReturn(Optional.empty());

        AssuntoNotFoundException exception = assertThrows(AssuntoNotFoundException.class, () -> livroService.save(request));

        assertEquals("Assunto com o id '1' não foi encontrado no sistema.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenLivroNotFoundForUpdate() {
        when(livroRepository.findById(1)).thenReturn(Optional.empty());

        LivroNotFoundException exception = assertThrows(LivroNotFoundException.class,
                () -> livroService.update(1, request()));

        assertEquals("Livro com o id '1' não foi encontrado no sistema.", exception.getMessage());
    }

    @Test
    void shouldFindLivroByIdAndInitializeRelations() {
        Livro livro = Livro.builder()
                .codL(1)
                .titulo("Clean Code")
                .livroAutores(new ArrayList<>())
                .livroAssuntos(new ArrayList<>())
                .build();
        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

        Livro result = livroService.findById(1);

        assertEquals(1, result.getCodL());
    }

    @Test
    void shouldDeleteLivroById() {
        when(livroRepository.existsById(1)).thenReturn(true);

        livroService.deleteById(1);

        verify(livroRepository).deleteById(1);
    }

    @Test
    void shouldThrowWhenLivroNotFoundForDeletion() {
        when(livroRepository.existsById(1)).thenReturn(false);

        LivroNotFoundException exception = assertThrows(LivroNotFoundException.class, () -> livroService.deleteById(1));

        assertEquals("Livro com o id '1' não foi encontrado no sistema.", exception.getMessage());
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
