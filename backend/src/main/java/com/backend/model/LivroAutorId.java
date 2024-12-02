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
public class LivroAutorId implements Serializable {

    @Column(name = "CodL") // Nome correto para a chave primária de Livro
    private Integer livroCodL;

    @Column(name = "CodAu") // Nome correto para a chave primária de Autor
    private Integer autorCodAu;
}
