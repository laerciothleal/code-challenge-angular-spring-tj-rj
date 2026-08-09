package com.backend.mapper;

import com.backend.controller.v1.request.CreateAssuntoRequest;
import com.backend.controller.v1.response.AssuntoResponse;
import com.backend.model.Assunto;
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
public interface AssuntoMapper {

    Assunto toEntity(CreateAssuntoRequest request);

    Assunto toEntity(CreateAssuntoRequest request, @MappingTarget Assunto entity);

    @Mapping(source = "codas", target = "codigoAs")
    AssuntoResponse toResponse(Assunto assunto);

    List<AssuntoResponse> toResponseList(List<Assunto> assuntos);
}
