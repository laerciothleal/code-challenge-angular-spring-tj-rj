package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroAssuntoId implements Serializable {

    @Column(name = "CodL") // Nome correto para a chave primária de Livro
    private Integer livroCodL;

    @Column(name = "CodAs") // Nome correto para a chave primária de Assunto
    private Integer assuntoCodAs;
}
