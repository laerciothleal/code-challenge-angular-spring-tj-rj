package com.backend.service;

import com.backend.controller.request.LivroRequest;
import com.backend.exception.ObjectNotFoundException;
import com.backend.mappper.LivroMapper;
import com.backend.model.*;
import com.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroAssuntoRepository livroAssuntoRepository;
    private final LivroAutorRepository livroAutorRepository;
    private final AssuntoRepository assuntoRepository;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;


    // Create Livro
    public Livro saveOrUpdateRelations(Livro livro, List<Integer> autoresIds, List<Integer> assuntosIds) {
        Livro savedLivro = saveLivroAndClearRelations(livro);

        saveLivroAutores(savedLivro, autoresIds);
        saveLivroAssuntos(savedLivro, assuntosIds);

        return savedLivro;
    }

    Livro saveLivroAndClearRelations(Livro livro) {
        Livro savedLivro = livroRepository.save(livro);

        // Limpar relações antigas de autores e assuntos
        livroAutorRepository.deleteByLivroCodL(savedLivro.getCodL());
        livroAssuntoRepository.deleteByLivroCodL(savedLivro.getCodL());

        return savedLivro;
    }

    void saveLivroAutores(Livro savedLivro, List<Integer> autoresIds) {
        for (Integer autorId : autoresIds) {
            if (autorId == null) {
                throw new IllegalArgumentException("ID do autor não pode ser nulo.");
            }

            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado: " + autorId));

            LivroAutor livroAutor = LivroAutor.builder()
                    .id(
                            LivroAutorId.builder()
                                    .livroCodL(savedLivro.getCodL())
                                    .autorCodAu(autor.getCodau())
                                    .build()
                    )
                    .autor(autor)
                    .livro(savedLivro)
                    .build();

            livroAutorRepository.save(livroAutor);
        }
    }

    void saveLivroAssuntos(Livro savedLivro, List<Integer> assuntosIds) {
        for (Integer assuntoId : assuntosIds) {
            Assunto assunto = assuntoRepository.findById(assuntoId)
                    .orElseThrow(() -> new IllegalArgumentException("Assunto não encontrado: " + assuntoId));

            LivroAssunto livroAssunto = LivroAssunto.builder()
                    .id(
                            LivroAssuntoId.builder()
                                    .livroCodL(savedLivro.getCodL())
                                    .assuntoCodAs(assunto.getCodas())
                                    .build()
                    )
                    .assunto(assunto)
                    .livro(savedLivro)
                    .build();

            livroAssuntoRepository.save(livroAssunto);
        }
    }

    //Update Livro
    public Livro update(final Integer id, final LivroRequest request) {
        Livro livroToUpdate = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        format("Livro não encontrado com id %s", id)
                ));

        // Atualizar informações básicas do livro
        livroToUpdate.setTitulo(request.titulo());
        livroToUpdate.setEditora(request.editora());
        livroToUpdate.setEdicao(request.edicao());
        livroToUpdate.setAnoPublicacao(request.anoPublicacao());
        livroToUpdate.setValor(request.valor());

        // Atualizar relações
        return saveOrUpdateRelations(livroToUpdate, request.autoresIds(), request.assuntosIds());
    }

    // Find Livro by Id
    public Optional<Livro> findById(Integer id) {
        return Optional.of(
                livroRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                                format("Livro não encontrado com id %s", id)
                        )
                )
        );
    }

    // Find all Livros
    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    // Delete Livro by Id
    public void deleteById(Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado para exclusão com Id: " + id);
        }
        livroRepository.deleteById(id);
    }

    // Check if Livro exists
    public boolean existsById(Integer id) {
        return livroRepository.existsById(id);
    }
}
