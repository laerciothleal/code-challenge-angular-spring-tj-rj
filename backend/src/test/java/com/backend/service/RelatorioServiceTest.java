package com.backend.service;

import com.backend.controller.v1.response.RelatorioResponse;
import com.backend.mapper.RelatorioMapper;
import com.backend.repository.LivroAutorViewRepository;
import com.backend.view.LivroAutorViewResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @InjectMocks
    private RelatorioService relatorioService;

    @Mock
    private LivroAutorViewRepository livroAutorViewRepository;

    @Mock
    private RelatorioMapper relatorioMapper;

    @Test
    void shouldMapViewToResponse() {
        LivroAutorViewResponse view = LivroAutorViewResponse.builder()
                .id(1L)
                .autorNome("Uncle Bob")
                .livroTitulo("Clean Code")
                .build();
        RelatorioResponse response = RelatorioResponse.builder()
                .id(1L)
                .autorNome("Uncle Bob")
                .livroTitulo("Clean Code")
                .livroValor(BigDecimal.TEN)
                .build();

        when(livroAutorViewRepository.findAll()).thenReturn(List.of(view));
        when(relatorioMapper.toResponseList(List.of(view))).thenReturn(List.of(response));

        List<RelatorioResponse> result = relatorioService.getLivrosPorAutor();

        assertEquals(1, result.size());
        assertEquals("Uncle Bob", result.get(0).autorNome());
    }
}
