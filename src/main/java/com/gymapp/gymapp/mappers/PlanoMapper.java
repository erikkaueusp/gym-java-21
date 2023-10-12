package com.gymapp.gymapp.mappers;

import com.gymapp.gymapp.domain.Plano;
import com.gymapp.gymapp.model.inputs.PlanoDtoInput;
import com.gymapp.gymapp.model.outputs.PlanoDtoOutput;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PlanoMapper {
    Plano toEntity(PlanoDtoInput input);

    PlanoDtoOutput toDto(Plano plano);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Plano updatePlanoFromPlanoDtoOutPut(PlanoDtoOutput planoDtoOutPut, @MappingTarget Plano plano);
}
