package com.deloitte.springaula.repository;

import com.deloitte.springaula.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);
    boolean existsByNome(String nome);
}
