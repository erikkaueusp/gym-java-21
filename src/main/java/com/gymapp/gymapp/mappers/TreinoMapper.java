package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Treino;
import com.gymapp.gymapp.model.TreinoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreinoMapper {
    TreinoDto toDto(Treino treino);

    Treino toEntity(TreinoDto treinoDto);
}
