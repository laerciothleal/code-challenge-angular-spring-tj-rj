package com.backend.service;

import com.backend.controller.v1.request.CreateAssuntoRequest;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.mappper.AssuntoMapper;
import com.backend.model.Assunto;
import com.backend.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    private final AssuntoMapper assuntoMapper;

    public Assunto save(CreateAssuntoRequest request) {
        var mapped = assuntoMapper.toEntity(request);
        return assuntoRepository.save(mapped);
    }

    public Assunto update(final Integer id, final CreateAssuntoRequest request) {
        return findById(id)
                .map(entity -> assuntoMapper.toEntity(request, entity))
                .map(assuntoRepository::save)
                .orElseThrow(() -> new AssuntoNotFoundException(id));

    }

    public Optional<Assunto> findById(Integer id) {
        return Optional.of(assuntoRepository.findById(id).orElseThrow(() -> new AssuntoNotFoundException(id)));
    }

    public List<Assunto> findAll() {
        return assuntoRepository.findAll();
    }

    public void deleteById(Integer id) {
        assuntoRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return assuntoRepository.existsById(id);
    }
}
