package com.backend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Livro_Assunto")
@Getter
@Setter
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
