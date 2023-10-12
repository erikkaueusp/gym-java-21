package com.gymapp.gymapp.service.treino;

import com.gymapp.gymapp.domain.Treino;
import com.gymapp.gymapp.mappers.TreinoMapper;
import com.gymapp.gymapp.model.inputs.TreinoDtoInput;
import com.gymapp.gymapp.model.outputs.TreinoDtoOutput;
import com.gymapp.gymapp.repository.TreinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository repository;

    @Autowired
    private TreinoMapper mapper;

    public TreinoDtoOutput findById(Long id) {
        Optional<Treino> exercicio = repository.findById(id);
        return mapper.toDto(exercicio.orElseThrow(EntityNotFoundException::new));
    }

    public Page<TreinoDtoOutput> findAll(Pageable pageable) {
        Page<Treino> treinos = repository.findAll(pageable);
        return treinos.map(mapper::toDto);
    }

    public Long save(TreinoDtoInput input) {
        Treino save = repository.save(mapper.toEntity(input));
        return save.getId();
    }
}
