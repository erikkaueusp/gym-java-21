package com.gymapp.gymapp.repository;

import com.gymapp.gymapp.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long>, JpaSpecificationExecutor<Exercicio> {
}