package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Assunto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assunto_seq")
    @SequenceGenerator(name = "assunto_seq", sequenceName = "seq_assunto", allocationSize = 1)
    private Integer codas;

    @Column(nullable = false, length = 20)
    private String descricao;
}
