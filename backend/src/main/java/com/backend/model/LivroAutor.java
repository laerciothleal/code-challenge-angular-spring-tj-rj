package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Livro_Autor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroAutor {

    @EmbeddedId
    private LivroAutorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("livroCodL")
    @JoinColumn(name = "CodL", nullable = false)
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("autorCodAu")
    @JoinColumn(name = "codau", nullable = false)
    private Autor autor;
}

