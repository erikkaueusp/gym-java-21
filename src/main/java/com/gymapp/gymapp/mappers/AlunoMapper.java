package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.model.AlunoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    AlunoDto toDto(Aluno aluno);

    Aluno toEntity(AlunoDto alunoDto);
}
