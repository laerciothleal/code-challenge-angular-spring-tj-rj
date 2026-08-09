package com.backend.controller.v1;

import com.backend.controller.v1.request.CreateAssuntoRequest;
import com.backend.controller.v1.response.AssuntoResponse;
import com.backend.mapper.AssuntoMapper;
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
@RequestMapping("/api/v1/assuntos")
@Tag(name = "Assunto Controller", description = "API para gerenciamento de assuntos")
@RequiredArgsConstructor
public class AssuntoController {

    private final AssuntoService assuntoService;
    private final AssuntoMapper assuntoMapper;

    @Operation(summary = "Criar um assunto", description = "Cria um assunto com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assunto criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AssuntoResponse> save(@Valid @RequestBody CreateAssuntoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assuntoMapper.toResponse(assuntoService.save(request)));
    }

    @Operation(summary = "Atualizar um assunto", description = "Atualiza um assunto com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assunto atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AssuntoResponse> patch(@Parameter(description = "Id do assunto") @PathVariable Integer id,
                                                 @Valid @RequestBody CreateAssuntoRequest request) {
        return ResponseEntity.ok(assuntoMapper.toResponse(assuntoService.update(id, request)));
    }

    @Operation(summary = "Obter assunto pelo Id", description = "Recupera um assunto com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assunto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssuntoResponse> getById(@Parameter(description = "Id do assunto") @PathVariable Integer id) {
        return ResponseEntity.ok(assuntoMapper.toResponse(assuntoService.findById(id)));
    }

    @Operation(summary = "Obter todos os assuntos", description = "Recupera todos os assuntos registrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de assuntos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoResponse.class)))
    @GetMapping
    public ResponseEntity<List<AssuntoResponse>> getAll() {
        return ResponseEntity.ok(assuntoMapper.toResponseList(assuntoService.findAll()));
    }

    @Operation(summary = "Excluir assunto por Id", description = "Exclui um assunto com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assunto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Id do assunto a ser excluído") @PathVariable Integer id) {
        assuntoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
