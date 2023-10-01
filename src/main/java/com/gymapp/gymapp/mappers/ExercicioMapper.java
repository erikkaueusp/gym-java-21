package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Exercicio;
import com.gymapp.gymapp.model.ExercicioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExercicioMapper {
    ExercicioDto toDto(Exercicio exercicio);
    
    Exercicio toEntity(ExercicioDto exercicioDto);
}
