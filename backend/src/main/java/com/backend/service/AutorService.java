package com.backend.service;

import com.backend.controller.v1.request.CreateAutorRequest;
import com.backend.exception.AutorNotFoundException;
import com.backend.mapper.AutorMapper;
import com.backend.model.Autor;
import com.backend.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Transactional
    public Autor save(CreateAutorRequest request) {
        Autor saved = autorRepository.save(autorMapper.toEntity(request));
        log.info("Autor created id={}", saved.getCodau());
        return saved;
    }

    @Transactional
    public Autor update(Integer id, CreateAutorRequest request) {
        Autor entity = findById(id);
        Autor saved = autorRepository.save(autorMapper.toEntity(request, entity));
        log.info("Autor updated id={}", id);
        return saved;
    }

    @Transactional(readOnly = true)
    public Autor findById(Integer id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!autorRepository.existsById(id)) {
            throw new AutorNotFoundException(id);
        }
        autorRepository.deleteById(id);
        log.info("Autor deleted id={}", id);
    }
}
