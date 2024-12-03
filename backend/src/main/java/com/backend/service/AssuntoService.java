package com.backend.service;

import com.backend.controller.request.AssuntoRequest;
import com.backend.exception.ObjectNotFoundException;
import com.backend.mappper.AssuntoMapper;
import com.backend.model.Assunto;
import com.backend.repository.AssuntoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class AssuntoService {

    private AssuntoRepository assuntoRepository;

    private final AssuntoMapper assuntoMapper;


    // Create or Update Assunto
    public Assunto save(AssuntoRequest request) {
        var mapped = assuntoMapper.toEntity(request);
        return assuntoRepository.save(mapped);
    }

    public Assunto update(final Integer id, final AssuntoRequest request) {
        return findById(id)
                .map(entity -> assuntoMapper.toEntity(request, entity)) // Update the entity
                .map(assuntoRepository::save) // Save the updated entity
                .orElseThrow(() -> new ObjectNotFoundException(
                        format("Erro ao atualizar Autor com o id %s", id)
                ));
    }

    // Find Assunto by Id
    public Optional<Assunto> findById(Integer id) {
        return Optional.of(
                assuntoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                                format("Livro não encontrado com id %s", id)
                        )
                )
        );
    }

    // Find all Assuntos
    public List<Assunto> findAll() {
        return assuntoRepository.findAll();
    }

    // Delete Assunto by Id
    public void deleteById(Integer id) {
        assuntoRepository.deleteById(id);
    }

    // Check if Assunto exists
    public boolean existsById(Integer id) {
        return assuntoRepository.existsById(id);
    }
}
