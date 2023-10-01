package com.gymapp.gymapp.repository;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {
}