package com.backend.service;

import com.backend.controller.v1.request.CreateAssuntoRequest;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.mapper.AssuntoMapper;
import com.backend.model.Assunto;
import com.backend.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Transactional
    public Assunto save(CreateAssuntoRequest request) {
        Assunto saved = assuntoRepository.save(assuntoMapper.toEntity(request));
        log.info("Assunto created id={}", saved.getCodas());
        return saved;
    }

    @Transactional
    public Assunto update(Integer id, CreateAssuntoRequest request) {
        Assunto entity = findById(id);
        Assunto saved = assuntoRepository.save(assuntoMapper.toEntity(request, entity));
        log.info("Assunto updated id={}", id);
        return saved;
    }

    @Transactional(readOnly = true)
    public Assunto findById(Integer id) {
        return assuntoRepository.findById(id)
                .orElseThrow(() -> new AssuntoNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Assunto> findAll() {
        return assuntoRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!assuntoRepository.existsById(id)) {
            throw new AssuntoNotFoundException(id);
        }
        assuntoRepository.deleteById(id);
        log.info("Assunto deleted id={}", id);
    }
}
