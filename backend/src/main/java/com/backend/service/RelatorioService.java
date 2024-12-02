package com.backend.service;

import com.backend.controller.response.RelatorioResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    private final JdbcTemplate jdbcTemplate;

    public RelatorioService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RelatorioResponse> getLivrosPorAutor() {
        String sql = "SELECT * FROM vw_livros_por_autor";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RelatorioResponse(

                rs.getString("autor_nome"),
                rs.getString("livro_assuntos"),
                rs.getString("livro_titulo"),
                rs.getString("livro_editora"),
                rs.getInt("livro_edicao"),
                rs.getString("livro_ano_publicacao"),
                rs.getBigDecimal("livro_valor")
        ));
    }
}
