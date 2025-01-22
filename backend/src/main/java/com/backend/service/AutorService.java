package com.backend.service;

import com.backend.controller.v1.request.CreateAutorRequest;
import com.backend.exception.AutorNotFoundException;
import com.backend.mappper.AutorMapper;
import com.backend.model.Autor;
import com.backend.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    private final AutorMapper autorMapper;

    public Autor save(CreateAutorRequest request) {
        return autorRepository.save(autorMapper.toEntity(request));
    }

    public Autor update(final Integer id, final CreateAutorRequest request) {
        return findById(id)
                .map(entity -> autorMapper.toEntity(request, entity))
                .map(autorRepository::save)
                .orElseThrow(() -> new AutorNotFoundException(id));
    }

    public Optional<Autor> findById(Integer id) {
        return Optional.of(autorRepository.findById(id).orElseThrow(() -> new AutorNotFoundException(id)));
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public void deleteById(Integer id) {
        autorRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return autorRepository.existsById(id);
    }
}
