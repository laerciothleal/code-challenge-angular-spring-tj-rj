package com.backend.repository;

import com.backend.model.LivroAssunto;
import com.backend.model.LivroAssuntoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroAssuntoRepository extends JpaRepository<LivroAssunto, LivroAssuntoId> {
    void deleteByLivroCodL(Integer livroCodL);
}

