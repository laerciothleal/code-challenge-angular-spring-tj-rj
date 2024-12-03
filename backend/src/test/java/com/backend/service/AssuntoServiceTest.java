package com.backend.service;

import com.backend.controller.request.AssuntoRequest;
import com.backend.exception.ObjectNotFoundException;
import com.backend.mappper.AssuntoMapper;
import com.backend.model.Assunto;
import com.backend.repository.AssuntoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssuntoServiceTest {

    @InjectMocks
    private AssuntoService assuntoService;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAssunto() {
        AssuntoRequest request = new AssuntoRequest("Assunto Teste");
        Assunto mappedAssunto = new Assunto(1, "Assunto Teste");

        when(assuntoMapper.toEntity(request)).thenReturn(mappedAssunto);
        when(assuntoRepository.save(mappedAssunto)).thenReturn(mappedAssunto);

        Assunto result = assuntoService.save(request);

        assertNotNull(result);
        assertEquals(mappedAssunto, result);
        verify(assuntoRepository, times(1)).save(mappedAssunto);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingAssunto() {
        AssuntoRequest request = new AssuntoRequest("Assunto Teste");
        int id = 1;

        when(assuntoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> assuntoService.update(id, request));
    }

    @Test
    void shouldDeleteAssuntoById() {
        int id = 1;
        doNothing().when(assuntoRepository).deleteById(id);

        assuntoService.deleteById(id);

        verify(assuntoRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindAssuntoById() {
        int id = 1;
        Assunto assunto = new Assunto(1, "Assunto Teste");

        when(assuntoRepository.findById(id)).thenReturn(Optional.of(assunto));

        Optional<Assunto> result = assuntoService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(assunto, result.get());
    }
}
