package com.backend.service;

import com.backend.controller.v1.response.RelatorioResponse;
import com.backend.mapper.RelatorioMapper;
import com.backend.repository.LivroAutorViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final LivroAutorViewRepository livroAutorViewRepository;
    private final RelatorioMapper relatorioMapper;

    @Transactional(readOnly = true)
    public List<RelatorioResponse> getLivrosPorAutor() {
        return relatorioMapper.toResponseList(livroAutorViewRepository.findAll());
    }
}
