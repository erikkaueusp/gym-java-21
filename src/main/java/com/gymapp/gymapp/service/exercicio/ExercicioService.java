package com.gymapp.gymapp.service.exercicio;

import com.gymapp.gymapp.domain.Exercicio;
import com.gymapp.gymapp.mappers.ExercicioMapper;
import com.gymapp.gymapp.model.ExercicioDto;
import com.gymapp.gymapp.repository.ExercicioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository repository;

    @Autowired
    private ExercicioMapper mapper;

    public ExercicioDto findById(Long id) {
        Optional<Exercicio> exercicio = repository.findById(id);
        return mapper.toDto(exercicio.orElseThrow(EntityNotFoundException::new));
    }

    public Page<ExercicioDto> findAll(Pageable pageable) {
        Page<Exercicio> exercicios = repository.findAll(pageable);
        return exercicios.map(mapper::toDto);
    }

    public Long save(ExercicioDto exercicioDto) {
        Exercicio save = repository.save(mapper.toEntity(exercicioDto));
        return save.getId();
    }
}
