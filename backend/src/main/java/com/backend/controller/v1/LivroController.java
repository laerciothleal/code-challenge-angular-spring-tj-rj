package com.backend.controller.v1;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.controller.v1.response.LivroResponse;
import com.backend.mapper.LivroMapper;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
@Tag(name = "Livro Controller", description = "API para gerenciamento de livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @Operation(summary = "Criar um livro", description = "Cria um livro com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    })
    @PostMapping
    public ResponseEntity<LivroResponse> save(@Valid @RequestBody CreateLivroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(livroMapper.toResponse(livroService.save(request)));
    }

    @Operation(summary = "Atualizar um livro", description = "Atualiza um livro com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<LivroResponse> patch(@Parameter(description = "Id do livro") @PathVariable Integer id,
                                               @Valid @RequestBody CreateLivroRequest request) {
        return ResponseEntity.ok(livroMapper.toResponse(livroService.update(id, request)));
    }

    @Operation(summary = "Obter livro pelo Id", description = "Recupera um livro com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> getById(@Parameter(description = "Id do livro") @PathVariable Integer id) {
        return ResponseEntity.ok(livroMapper.toResponse(livroService.findById(id)));
    }

    @Operation(summary = "Obter todos os livros", description = "Recupera todos os livros registrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de livros",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class)))
    @GetMapping
    public ResponseEntity<List<LivroResponse>> getAll() {
        return ResponseEntity.ok(livroMapper.toResponseList(livroService.findAll()));
    }

    @Operation(summary = "Excluir livro por Id", description = "Exclui um livro com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Id do livro a ser excluído") @PathVariable Integer id) {
        livroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
