package com.backend.repository;

import com.backend.model.LivroAutor;
import com.backend.model.LivroAutorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroAutorRepository extends JpaRepository<LivroAutor, LivroAutorId> {
    void deleteByLivroCodL(Integer livroCodL);
}
