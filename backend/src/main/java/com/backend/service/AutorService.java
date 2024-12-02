package com.backend.service;

import com.backend.controller.request.AutorRequest;
import com.backend.exception.ObjectNotFoundException;
import com.backend.mappper.AutorMapper;
import com.backend.model.Autor;
import com.backend.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    private final AutorMapper autorMapper;

    public AutorService(AutorRepository autorRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }

    // Create or Update Autor
    public Autor save(AutorRequest request) {
        return autorRepository.save(autorMapper.toEntity(request));
    }

    public Autor update(final Integer id, final AutorRequest request) {
        return findById(id)
                .map(entity -> autorMapper.toEntity(request, entity)) // Update the entity
                .map(autorRepository::save) // Save the updated entity
                .orElseThrow(() -> new ObjectNotFoundException(
                        format("Erro ao atualizar Autor com o id %s", id)
                ));
    }

    // Find Autor by Id
    public Optional<Autor> findById(Integer id) {
        return Optional.of(
                autorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                                format("Livro não encontrado com id %s", id)
                        )
                )
        );
    }

    // Find all Autores
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    // Delete Autor by Id
    public void deleteById(Integer id) {
        autorRepository.deleteById(id);
    }

    // Check if Autor exists
    public boolean existsById(Integer id) {
        return autorRepository.existsById(id);
    }
}
