package com.backend.mappper;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.model.Livro;
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
public interface LivroMapper {

    Livro toEntity(final CreateLivroRequest request);

    Livro toEntity(final CreateLivroRequest request, @MappingTarget final Livro entity);

}
