package com.backend.controller;

import com.backend.controller.request.AutorRequest;
import com.backend.controller.response.AutorResponse;
import com.backend.model.Autor;
import com.backend.model.Livro;
import com.backend.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autor")
@Tag(name = "Autor Controller", description = "API para gerenciamento de autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }


    @Operation(summary = "Criar um autor", description = "Cria um autor com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Autor> save(@Valid @RequestBody AutorRequest autorRequest) {
        Autor savedAutor = autorService.save(autorRequest);
        return new ResponseEntity<>(savedAutor, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um autor", description = "Atualiza um livro com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"), @ApiResponse(responseCode = "500", description = "Erro no servidor")})
    @PatchMapping("/{id}")
    public ResponseEntity<Autor> patch(@Parameter(description = "Id do livro") @PathVariable Integer id,@Valid @RequestBody AutorRequest livroRequest) {
        Autor livro = autorService.update(id, livroRequest);
        return new ResponseEntity(livro, HttpStatus.OK);

    }

    @Operation(summary = "Obter autor pelo Id", description = "Recupera um autor com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> getById(@Parameter(description = "Id do autor") @PathVariable Integer id) {
        Optional<Autor> autor = autorService.findById(id);
        return new ResponseEntity(autor.get(), HttpStatus.OK);
    }

    @Operation(summary = "Obter todos os autores", description = "Recupera todos os autores registrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de autores", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    @GetMapping
    public ResponseEntity<List<Autor>> getAll() {
        List<Autor> autores = autorService.findAll();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @Operation(summary = "Excluir autor por Id", description = "Exclui um autor com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Id do autor a ser excluído") @PathVariable Integer id) {
        if (autorService.existsById(id)) {
            autorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
