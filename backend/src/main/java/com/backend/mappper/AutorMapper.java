package com.backend.mappper;

import com.backend.controller.request.AutorRequest;
import com.backend.model.Autor;
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
public interface AutorMapper {

    Autor toEntity(final AutorRequest request);

    Autor toEntity(final AutorRequest request, @MappingTarget final Autor entity);
}
