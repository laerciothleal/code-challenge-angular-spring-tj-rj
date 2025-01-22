package com.backend.service;

import com.backend.repository.LivroAutorViewRepository;
import com.backend.view.LivroAutorViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final LivroAutorViewRepository livroAutorViewRepository;

    public List<LivroAutorViewResponse> getLivrosPorAutor() {
        return livroAutorViewRepository.findAll();
    }

}
