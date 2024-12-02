package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Livro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livro_seq")
    @SequenceGenerator(name = "livro_seq", sequenceName = "seq_livro", allocationSize = 1)
    @Column(name = "CodL")
    private Integer codL;

    @Column(nullable = false, length = 40)
    private String titulo;

    @Column(nullable = false, length = 40)
    private String editora;

    @Column(nullable = false)
    private Integer edicao;

    @Column(nullable = false, length = 4)
    private String anoPublicacao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LivroAutor> livroAutores;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LivroAssunto> livroAssuntos;

}
