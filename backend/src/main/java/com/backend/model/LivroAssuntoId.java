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

    @Column(name = "CodL")
    private Integer livroCodL;

    @Column(name = "CodAs")
    private Integer assuntoCodAs;
}
