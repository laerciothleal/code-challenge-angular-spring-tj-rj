package com.backend.controller;

import com.backend.controller.request.AssuntoRequest;
import com.backend.controller.response.AssuntoResponse;
import com.backend.model.Assunto;
import com.backend.model.Livro;
import com.backend.service.AssuntoService;
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

@RestController
@RequestMapping("/api/assunto")
@Tag(name = "Assunto Controller", description = "API para gerenciamento de assuntos")
@RequiredArgsConstructor
public class AssuntoController {

    private final AssuntoService assuntoService;

    @Operation(summary = "Criar um assunto", description = "Cria um assunto com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assunto criado ou atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Assunto> save(@RequestBody AssuntoRequest assuntoRequest) {
        Assunto savedAssunto = assuntoService.save(assuntoRequest);
        return new ResponseEntity<>(savedAssunto, HttpStatus.CREATED);
    }


    @Operation(summary = "Atualizar um assunto", description = "Atualiza um assunto com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assunto atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Assunto> patch(@Parameter(description = "Id do assunto") @PathVariable Integer id,
                                         @Valid @RequestBody AssuntoRequest assuntoRequest) {
        Assunto assunto = assuntoService.update(id, assuntoRequest);
        return new ResponseEntity(assunto, HttpStatus.OK);

    }

    @Operation(summary = "Obter assunto pelo Id", description = "Recupera um assunto com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assunto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<Assunto> getById(@Parameter(description = "Id do assunto") @PathVariable Integer id) {
        Optional<Assunto> assunto = assuntoService.findById(id);
        return new ResponseEntity(assunto.get(), HttpStatus.OK);
    }

    @Operation(summary = "Obter todos os assuntos", description = "Recupera todos os assuntos registrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de assuntos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class)))
    @GetMapping
    public ResponseEntity<List<Assunto>> getAll() {
        List<Assunto> assuntos = assuntoService.findAll();
        return new ResponseEntity<>(assuntos, HttpStatus.OK);
    }

    @Operation(summary = "Excluir assunto por Id", description = "Exclui um assunto com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assunto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Id do assunto a ser excluído") @PathVariable Integer id) {
        if (assuntoService.existsById(id)) {
            assuntoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
