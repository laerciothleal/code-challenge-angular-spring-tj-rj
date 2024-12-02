package com.backend.controller;

import com.backend.controller.request.LivroRequest;
import com.backend.controller.response.AssuntoResponse;
import com.backend.controller.response.AutorResponse;
import com.backend.controller.response.LivroResponse;
import com.backend.mappper.LivroMapper;
import com.backend.model.Livro;
import com.backend.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livro")
@Tag(name = "Livro Controller", description = "API para gerenciamento de livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    private final LivroMapper livroMapper;

    @Operation(summary = "Criar um livro", description = "Cria um livro com base nas informações fornecidas.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Livro criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class)))})
    @PostMapping
    public ResponseEntity<Livro> save(@Valid @RequestBody LivroRequest livroRequest) {

        Livro livro = livroMapper.toEntity(livroRequest);
        Livro savedLivro = livroService.saveOrUpdateRelations(
                livro,
                livroRequest.autoresIds(),
                livroRequest.assuntosIds()
        );

        return new ResponseEntity<>(savedLivro, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um livro", description = "Atualiza um livro com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Livro> patch(@Parameter(description = "Id do livro") @PathVariable Integer id,
                                       @Valid @RequestBody LivroRequest livroRequest) {

        Livro updatedLivro = livroService.update(id, livroRequest);
        return new ResponseEntity<>(updatedLivro, HttpStatus.OK);
    }


    @Operation(summary = "Obter livro pelo Id", description = "Recupera um livro com base no seu Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Livro encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))), @ApiResponse(responseCode = "404", description = "Livro não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> getById(@Parameter(description = "Id do livro") @PathVariable Integer id) {
        Optional<Livro> livro = livroService.findById(id);

        // Mapear os relacionamentos para os DTOs
        List<AutorResponse> autores = livro.get().getLivroAutores().stream()
                .map(la -> new AutorResponse(la.getAutor().getCodau(), la.getAutor().getNome()))
                .collect(Collectors.toList());

        List<AssuntoResponse> assuntos = livro.get().getLivroAssuntos().stream()
                .map(la -> new AssuntoResponse(la.getAssunto().getCodas(), la.getAssunto().getDescricao()))
                .collect(Collectors.toList());


        return new ResponseEntity<>(new LivroResponse(
                livro.get().getCodL(),
                livro.get().getTitulo(),
                livro.get().getEditora(),
                livro.get().getEdicao(),
                livro.get().getAnoPublicacao(),
                livro.get().getValor(),
                autores,
                assuntos
        ), HttpStatus.OK);
    }

    @Operation(summary = "Obter todos os livros", description = "Recupera todos os livros registrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de livros", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class)))
    @GetMapping
    public ResponseEntity<List<Livro>> getAll() {
        List<Livro> livros = livroService.findAll();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @Operation(summary = "Excluir livro por Id", description = "Exclui um livro com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"), @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Id do livro a ser excluído") @PathVariable Integer id) {
        if (livroService.existsById(id)) {
            livroService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
