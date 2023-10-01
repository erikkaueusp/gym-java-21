package com.gymapp.gymapp.service.aluno;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.mappers.AlunoMapper;
import com.gymapp.gymapp.model.AlunoDto;
import com.gymapp.gymapp.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private AlunoMapper mapper;

    public AlunoDto findById(Long id) {
        Optional<Aluno> exercicio = repository.findById(id);
        return mapper.toDto(exercicio.orElseThrow(EntityNotFoundException::new));
    }

    public Page<AlunoDto> findAll(Pageable pageable) {
        Page<Aluno> alunos = repository.findAll(pageable);
        return alunos.map(mapper::toDto);
    }

    public Long save(AlunoDto alunoDto) {
        Aluno save = repository.save(mapper.toEntity(alunoDto));
        return save.getId();
    }
}
