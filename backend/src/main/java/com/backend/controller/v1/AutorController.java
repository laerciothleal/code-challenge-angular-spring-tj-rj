package com.backend.controller.v1;

import com.backend.controller.v1.request.CreateAutorRequest;
import com.backend.controller.v1.response.AutorResponse;
import com.backend.mapper.AutorMapper;
import com.backend.service.AutorService;
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
@RequestMapping("/api/v1/autores")
@Tag(name = "Autor Controller", description = "API para gerenciamento de autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @Operation(summary = "Criar um autor", description = "Cria um autor com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AutorResponse> save(@Valid @RequestBody CreateAutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(autorMapper.toResponse(autorService.save(request)));
    }

    @Operation(summary = "Atualizar um autor", description = "Atualiza um autor com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AutorResponse> patch(@Parameter(description = "Id do autor") @PathVariable Integer id,
                                               @Valid @RequestBody CreateAutorRequest request) {
        return ResponseEntity.ok(autorMapper.toResponse(autorService.update(id, request)));
    }

    @Operation(summary = "Obter autor pelo Id", description = "Recupera um autor com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> getById(@Parameter(description = "Id do autor") @PathVariable Integer id) {
        return ResponseEntity.ok(autorMapper.toResponse(autorService.findById(id)));
    }

    @Operation(summary = "Obter todos os autores", description = "Recupera todos os autores registrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de autores",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class)))
    @GetMapping
    public ResponseEntity<List<AutorResponse>> getAll() {
        return ResponseEntity.ok(autorMapper.toResponseList(autorService.findAll()));
    }

    @Operation(summary = "Excluir autor por Id", description = "Exclui um autor com base no seu Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Id do autor a ser excluído") @PathVariable Integer id) {
        autorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
