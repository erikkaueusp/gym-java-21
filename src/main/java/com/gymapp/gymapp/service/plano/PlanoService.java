package com.gymapp.gymapp.service.plano;

import com.gymapp.gymapp.domain.Plano;
import com.gymapp.gymapp.mappers.PlanoMapper;
import com.gymapp.gymapp.model.inputs.PlanoDtoInput;
import com.gymapp.gymapp.model.outputs.PlanoDtoOutput;
import com.gymapp.gymapp.repository.PlanoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository repository;

    @Autowired
    private PlanoMapper mapper;

    public PlanoDtoOutput findById(Long id) {
        Optional<Plano> plano = repository.findById(id);
        return mapper.toDto(plano.orElseThrow(EntityNotFoundException::new));
    }

    public Page<PlanoDtoOutput> findAll(Pageable pageable) {
        Page<Plano> planos = repository.findAll(pageable);
        return planos.map(mapper::toDto);
    }

    public Long save(PlanoDtoInput input) {
        Plano save = repository.save(mapper.toEntity(input));
        return save.getId();
    }
}
