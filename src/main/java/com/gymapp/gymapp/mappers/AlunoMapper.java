package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.model.inputs.AlunotDtoInput;
import com.gymapp.gymapp.model.outputs.AlunoDtoOutput;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    @BeanMapping(ignoreUnmappedSourceProperties = {"id"})
    AlunoDtoOutput toDto(Aluno aluno);

    Aluno toEntity(AlunotDtoInput alunoDto);
}
