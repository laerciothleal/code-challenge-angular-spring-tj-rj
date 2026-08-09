package com.backend.service;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.exception.AssuntoNotFoundException;
import com.backend.exception.AutorNotFoundException;
import com.backend.exception.LivroNotFoundException;
import com.backend.mapper.LivroMapper;
import com.backend.model.Assunto;
import com.backend.model.Autor;
import com.backend.model.Livro;
import com.backend.model.LivroAssunto;
import com.backend.model.LivroAssuntoId;
import com.backend.model.LivroAutor;
import com.backend.model.LivroAutorId;
import com.backend.repository.AssuntoRepository;
import com.backend.repository.AutorRepository;
import com.backend.repository.LivroAssuntoRepository;
import com.backend.repository.LivroAutorRepository;
import com.backend.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroAssuntoRepository livroAssuntoRepository;
    private final LivroAutorRepository livroAutorRepository;
    private final AssuntoRepository assuntoRepository;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;

    @Transactional
    public Livro save(CreateLivroRequest request) {
        Livro saved = saveOrUpdateRelations(
                livroMapper.toEntity(request),
                request.autoresIds(),
                request.assuntosIds());
        log.info("Livro created id={}", saved.getCodL());
        return saved;
    }

    @Transactional
    public Livro update(Integer id, CreateLivroRequest request) {
        Livro livroToUpdate = getExisting(id);
        livroToUpdate.setTitulo(request.titulo());
        livroToUpdate.setEditora(request.editora());
        livroToUpdate.setEdicao(request.edicao());
        livroToUpdate.setAnoPublicacao(request.anoPublicacao());
        livroToUpdate.setValor(request.valor());

        Livro saved = saveOrUpdateRelations(livroToUpdate, request.autoresIds(), request.assuntosIds());
        log.info("Livro updated id={}", id);
        return saved;
    }

    @Transactional(readOnly = true)
    public Livro findById(Integer id) {
        Livro livro = getExisting(id);
        initializeRelations(livro);
        return livro;
    }

    @Transactional(readOnly = true)
    public List<Livro> findAll() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(this::initializeRelations);
        return livros;
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroNotFoundException(id);
        }
        livroRepository.deleteById(id);
        log.info("Livro deleted id={}", id);
    }

    private Livro getExisting(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
    }

    private Livro saveOrUpdateRelations(Livro livro, List<Integer> autoresIds, List<Integer> assuntosIds) {
        Livro savedLivro = saveLivroAndClearRelations(livro);
        savedLivro.setLivroAutores(saveLivroAutores(savedLivro, autoresIds));
        savedLivro.setLivroAssuntos(saveLivroAssuntos(savedLivro, assuntosIds));
        return savedLivro;
    }

    private Livro saveLivroAndClearRelations(Livro livro) {
        Livro savedLivro = livroRepository.save(livro);
        livroAutorRepository.deleteByLivroCodL(savedLivro.getCodL());
        livroAssuntoRepository.deleteByLivroCodL(savedLivro.getCodL());
        return savedLivro;
    }

    private List<LivroAutor> saveLivroAutores(Livro savedLivro, List<Integer> autoresIds) {
        List<LivroAutor> relations = new ArrayList<>();
        for (Integer autorId : autoresIds) {
            if (autorId == null) {
                throw new IllegalArgumentException("Id do autor não pode ser nulo.");
            }

            Autor autor = autorRepository.findById(autorId)
                    .orElseThrow(() -> new AutorNotFoundException(autorId));

            LivroAutor livroAutor = LivroAutor.builder()
                    .id(LivroAutorId.builder()
                            .livroCodL(savedLivro.getCodL())
                            .autorCodAu(autor.getCodau())
                            .build())
                    .autor(autor)
                    .livro(savedLivro)
                    .build();

            relations.add(livroAutorRepository.save(livroAutor));
        }
        return relations;
    }

    private List<LivroAssunto> saveLivroAssuntos(Livro savedLivro, List<Integer> assuntosIds) {
        List<LivroAssunto> relations = new ArrayList<>();
        for (Integer assuntoId : assuntosIds) {
            if (assuntoId == null) {
                throw new IllegalArgumentException("Id do assunto não pode ser nulo.");
            }

            Assunto assunto = assuntoRepository.findById(assuntoId)
                    .orElseThrow(() -> new AssuntoNotFoundException(assuntoId));

            LivroAssunto livroAssunto = LivroAssunto.builder()
                    .id(LivroAssuntoId.builder()
                            .livroCodL(savedLivro.getCodL())
                            .assuntoCodAs(assunto.getCodas())
                            .build())
                    .assunto(assunto)
                    .livro(savedLivro)
                    .build();

            relations.add(livroAssuntoRepository.save(livroAssunto));
        }
        return relations;
    }

    private void initializeRelations(Livro livro) {
        if (livro.getLivroAutores() != null) {
            livro.getLivroAutores().forEach(rel -> rel.getAutor().getNome());
        }
        if (livro.getLivroAssuntos() != null) {
            livro.getLivroAssuntos().forEach(rel -> rel.getAssunto().getDescricao());
        }
    }
}
