package com.gymapp.gymapp.repository;

import com.gymapp.gymapp.domain.Perfis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfisRepository extends JpaRepository<Perfis, Long> {

    Optional<Perfis> findByNome(String roleFuncionario);
}
