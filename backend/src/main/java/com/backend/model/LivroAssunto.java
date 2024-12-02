package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Livro_Assunto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroAssunto {

    @EmbeddedId
    private LivroAssuntoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("livroCodL")
    @JoinColumn(name = "CodL", nullable = false)
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assuntoCodAs")
    @JoinColumn(name = "codas", nullable = false)
    private Assunto assunto;
}

