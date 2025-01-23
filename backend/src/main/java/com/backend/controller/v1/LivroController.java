package com.backend.controller.v1;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.controller.v1.response.LivroResponse;
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

@RestController
@RequestMapping("/api/v1/livros")
@Tag(name = "Livro Controller", description = "API para gerenciamento de livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    private final LivroMapper livroMapper;

    @Operation(summary = "Criar um livro", description = "Cria um livro com base nas informações fornecidas.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Livro criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class)))})
    @PostMapping
    public ResponseEntity<Livro> save(@Valid @RequestBody CreateLivroRequest createLivroRequest) {

        Livro livro = livroMapper.toEntity(createLivroRequest);
        Livro savedLivro = livroService.saveOrUpdateRelations(
                livro,
                createLivroRequest.autoresIds(),
                createLivroRequest.assuntosIds()
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
                                       @Valid @RequestBody CreateLivroRequest createLivroRequest) {

        Livro updatedLivro = livroService.update(id, createLivroRequest);
        return new ResponseEntity<>(updatedLivro, HttpStatus.OK);
    }


    @Operation(summary = "Obter livro pelo Id", description = "Recupera um livro com base no seu Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Livro encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))), @ApiResponse(responseCode = "404", description = "Livro não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> getById(@Parameter(description = "Id do livro") @PathVariable Integer id) {
        return livroService.findById(id)
                .map(livroMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
