package com.backend.controller.v1;

import com.backend.controller.v1.response.RelatorioResponse;
import com.backend.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relatorios")
@Tag(name = "Relatório Controller", description = "API para buscar informações do relatório")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Operation(summary = "Recupera view para montar o retorno do relatório",
            description = "Buscar informações para montar a tela do relatório.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca do relatório realizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelatorioResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/livros-por-autor")
    public ResponseEntity<List<RelatorioResponse>> getLivrosPorAutor() {
        return ResponseEntity.ok(relatorioService.getLivrosPorAutor());
    }
}
