package com.backend.mapper;

import com.backend.controller.v1.response.RelatorioResponse;
import com.backend.view.LivroAutorViewResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RelatorioMapper {

    RelatorioResponse toResponse(LivroAutorViewResponse view);

    List<RelatorioResponse> toResponseList(List<LivroAutorViewResponse> views);
}
