package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Treino;
import com.gymapp.gymapp.model.inputs.TreinoDtoInput;
import com.gymapp.gymapp.model.outputs.TreinoDtoOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreinoMapper {
    TreinoDtoOutput toDto(Treino treino);

    @Mapping(source = "alunoId", target = "aluno.id")
    @Mapping(source = "exercicioId", target = "exercicio.id")
    Treino toEntity(TreinoDtoInput treinoDto);
}
