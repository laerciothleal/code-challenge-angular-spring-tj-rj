package com.backend.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_livros_por_autor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroAutorViewResponse {

    @Id
    private Long id;
    private String autorNome;
    private String livroAssuntos;
    private String livroTitulo;
    private String livroEditora;
    private Integer livroEdicao;
    private String livroAnoPublicacao;
    private BigDecimal livroValor;
}
