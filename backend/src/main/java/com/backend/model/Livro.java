package com.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Livro")
@Getter
@Setter
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
    @Builder.Default
    private List<LivroAutor> livroAutores = new ArrayList<>();

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<LivroAssunto> livroAssuntos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livro other)) {
            return false;
        }
        return codL != null && codL.equals(other.codL);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
