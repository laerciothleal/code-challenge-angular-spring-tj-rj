package com.backend.service;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.exception.AutorNotFoundException;
import com.backend.exception.LivroNotFoundException;
import com.backend.model.*;
import com.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Livro saveOrUpdateRelations(Livro livro, List<Integer> autoresIds, List<Integer> assuntosIds) {
        Livro savedLivro = saveLivroAndClearRelations(livro);

        saveLivroAutores(savedLivro, autoresIds);
        saveLivroAssuntos(savedLivro, assuntosIds);

        return savedLivro;
    }

    Livro saveLivroAndClearRelations(Livro livro) {
        Livro savedLivro = livroRepository.save(livro);

        livroAutorRepository.deleteByLivroCodL(savedLivro.getCodL());
        livroAssuntoRepository.deleteByLivroCodL(savedLivro.getCodL());

        return savedLivro;
    }

    void saveLivroAutores(Livro savedLivro, List<Integer> autoresIds) {
        for (Integer autorId : autoresIds) {
            if (autorId == null) {
                throw new IllegalArgumentException("Id do autor não pode ser nulo.");
            }

            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new AutorNotFoundException(autorId));

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
                    .orElseThrow(() -> new AssuntoNotFoundException(assuntoId));

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

    public Livro update(final Integer id, final CreateLivroRequest request) {
        Livro livroToUpdate = findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));

        livroToUpdate.setTitulo(request.titulo());
        livroToUpdate.setEditora(request.editora());
        livroToUpdate.setEdicao(request.edicao());
        livroToUpdate.setAnoPublicacao(request.anoPublicacao());
        livroToUpdate.setValor(request.valor());

        return saveOrUpdateRelations(livroToUpdate, request.autoresIds(), request.assuntosIds());
    }

    public Optional<Livro> findById(Integer id) {
        return Optional.of(livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException(id)));
    }

    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    public void deleteById(Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Livro não encontrado para exclusão com Id: '%s'",   id));
        }
        livroRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return livroRepository.existsById(id);
    }
}
