package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.model.inputs.AlunotDtoInput;
import com.gymapp.gymapp.model.outputs.AlunoDtoOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    AlunoDtoOutput toDto(Aluno aluno);

    Aluno toEntity(AlunotDtoInput alunoDto);
}
