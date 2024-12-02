package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Autor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autor_seq")
    @SequenceGenerator(name = "autor_seq", sequenceName = "seq_autor", allocationSize = 1)
    private Integer codau;

    @Column(name = "Nome")
    private String nome;
}
