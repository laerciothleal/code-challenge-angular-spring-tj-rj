package com.backend.mappper;

import com.backend.controller.request.AssuntoRequest;
import com.backend.model.Assunto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS,
        builder = @Builder
)
public interface AssuntoMapper {

    Assunto toEntity(final AssuntoRequest request);

    Assunto toEntity(final AssuntoRequest request, @MappingTarget final Assunto entity);
}
