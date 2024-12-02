package com.backend.controller;

import com.backend.controller.response.RelatorioResponse;
import com.backend.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;
    @GetMapping("/livros-por-autor")
    public ResponseEntity<List<RelatorioResponse>> getLivrosPorAutor() {
        List<RelatorioResponse> relatorio = relatorioService.getLivrosPorAutor();
        return ResponseEntity.ok(relatorio);
    }
}
