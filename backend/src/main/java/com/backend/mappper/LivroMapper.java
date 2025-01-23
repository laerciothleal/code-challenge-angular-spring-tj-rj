package com.backend.mappper;

import com.backend.controller.v1.request.CreateLivroRequest;
import com.backend.controller.v1.response.AssuntoResponse;
import com.backend.controller.v1.response.AutorResponse;
import com.backend.controller.v1.response.LivroResponse;
import com.backend.model.Livro;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

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

    default LivroResponse toResponse(@MappingTarget final Livro livro) {

        List<AutorResponse> autores = livro.getLivroAutores().stream()
                .map(autor -> AutorResponse.builder()
                        .codAu(autor.getAutor().getCodau())
                        .nome(autor.getAutor().getNome())
                        .build()
                )
                .collect(Collectors.toList());

        List<AssuntoResponse> assuntos = livro.getLivroAssuntos().stream()
                .map(assunto -> AssuntoResponse.builder()
                        .codigoAs(assunto.getAssunto().getCodas())
                        .descricao(assunto.getAssunto().getDescricao())
                        .build()
                )
                .collect(Collectors.toList());

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

}
