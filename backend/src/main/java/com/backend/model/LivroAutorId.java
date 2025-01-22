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

    @Column(name = "CodL")
    private Integer livroCodL;

    @Column(name = "CodAu")
    private Integer autorCodAu;
}
