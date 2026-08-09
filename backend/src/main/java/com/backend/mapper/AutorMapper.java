package com.backend.mapper;

import com.backend.controller.v1.request.CreateAutorRequest;
import com.backend.controller.v1.response.AutorResponse;
import com.backend.model.Autor;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS,
        builder = @Builder
)
public interface AutorMapper {

    Autor toEntity(CreateAutorRequest request);

    Autor toEntity(CreateAutorRequest request, @MappingTarget Autor entity);

    @Mapping(source = "codau", target = "codAu")
    AutorResponse toResponse(Autor autor);

    List<AutorResponse> toResponseList(List<Autor> autores);
}
