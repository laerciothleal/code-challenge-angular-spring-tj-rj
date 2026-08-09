package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Assunto")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assunto other)) {
            return false;
        }
        return codas != null && codas.equals(other.codas);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
