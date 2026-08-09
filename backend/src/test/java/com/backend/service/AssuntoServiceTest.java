package com.backend.service;

import com.backend.controller.v1.request.CreateAssuntoRequest;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.mapper.AssuntoMapper;
import com.backend.model.Assunto;
import com.backend.repository.AssuntoRepository;
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
class AssuntoServiceTest {

    @InjectMocks
    private AssuntoService assuntoService;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @Test
    void shouldSaveAssunto() {
        CreateAssuntoRequest request = new CreateAssuntoRequest("Assunto Teste");
        Assunto mapped = new Assunto(null, "Assunto Teste");
        Assunto saved = new Assunto(1, "Assunto Teste");

        when(assuntoMapper.toEntity(request)).thenReturn(mapped);
        when(assuntoRepository.save(mapped)).thenReturn(saved);

        Assunto result = assuntoService.save(request);

        assertEquals(1, result.getCodas());
        verify(assuntoRepository).save(mapped);
    }

    @Test
    void shouldThrowWhenUpdatingMissingAssunto() {
        CreateAssuntoRequest request = new CreateAssuntoRequest("Assunto Teste");
        when(assuntoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AssuntoNotFoundException.class, () -> assuntoService.update(1, request));
    }

    @Test
    void shouldFindAssuntoById() {
        Assunto assunto = new Assunto(1, "Assunto Teste");
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));

        assertEquals(assunto, assuntoService.findById(1));
    }

    @Test
    void shouldFindAllAssuntos() {
        when(assuntoRepository.findAll()).thenReturn(List.of(new Assunto(1, "A")));

        assertEquals(1, assuntoService.findAll().size());
    }

    @Test
    void shouldDeleteAssuntoById() {
        when(assuntoRepository.existsById(1)).thenReturn(true);
        doNothing().when(assuntoRepository).deleteById(1);

        assuntoService.deleteById(1);

        verify(assuntoRepository).deleteById(1);
    }

    @Test
    void shouldThrowWhenDeletingMissingAssunto() {
        when(assuntoRepository.existsById(1)).thenReturn(false);

        assertThrows(AssuntoNotFoundException.class, () -> assuntoService.deleteById(1));
    }
}
