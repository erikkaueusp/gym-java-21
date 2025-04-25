package com.gymapp.gymapp.repository;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.model.outputs.AutocompleteOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {

    @Query("SELECT a.id AS id, a.nome AS nome FROM Aluno a WHERE LOWER(a.nome) LIKE LOWER(CONCAT(:nome, '%'))")
    Page<AutocompleteOutput> findByNomeStartingWithIgnoreCase(@Param("nome") String nome, Pageable pageable);
}