package com.backend.mapper;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.controller.v1.response.AssuntoResponse;
import com.backend.controller.v1.response.AutorResponse;
import com.backend.controller.v1.response.LivroResponse;
import com.backend.model.Livro;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder
)
public interface LivroMapper {

    @Mapping(target = "codL", ignore = true)
    @Mapping(target = "livroAutores", ignore = true)
    @Mapping(target = "livroAssuntos", ignore = true)
    Livro toEntity(CreateLivroRequest request);

    @Mapping(target = "codL", ignore = true)
    @Mapping(target = "livroAutores", ignore = true)
    @Mapping(target = "livroAssuntos", ignore = true)
    Livro toEntity(CreateLivroRequest request, @MappingTarget Livro entity);

    default LivroResponse toResponse(Livro livro) {
        List<AutorResponse> autores = safeList(livro.getLivroAutores()).stream()
                .map(rel -> AutorResponse.builder()
                        .codAu(rel.getAutor().getCodau())
                        .nome(rel.getAutor().getNome())
                        .build())
                .toList();

        List<AssuntoResponse> assuntos = safeList(livro.getLivroAssuntos()).stream()
                .map(rel -> AssuntoResponse.builder()
                        .codigoAs(rel.getAssunto().getCodas())
                        .descricao(rel.getAssunto().getDescricao())
                        .build())
                .toList();

        return LivroResponse.builder()
                .codL(livro.getCodL())
                .titulo(livro.getTitulo())
                .editora(livro.getEditora())
                .edicao(livro.getEdicao())
                .anoPublicacao(livro.getAnoPublicacao())
                .valor(livro.getValor())
                .autores(autores)
                .assuntos(assuntos)
                .build();
    }

    default List<LivroResponse> toResponseList(List<Livro> livros) {
        return safeList(livros).stream().map(this::toResponse).toList();
    }

    private static <T> List<T> safeList(List<T> values) {
        return values == null ? List.of() : values;
    }
}
