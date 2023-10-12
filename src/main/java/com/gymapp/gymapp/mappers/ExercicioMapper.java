package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Exercicio;
import com.gymapp.gymapp.model.inputs.ExercicioDtoInput;
import com.gymapp.gymapp.model.outputs.ExercicioDtoOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExercicioMapper {
    ExercicioDtoOutput toDto(Exercicio entity);
    
    Exercicio toEntity(ExercicioDtoInput input);
}
