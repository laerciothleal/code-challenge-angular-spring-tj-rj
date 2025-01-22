CREATE OR replace VIEW vw_livros_por_autor AS
SELECT
    a.nome AS autor_nome,
    l.titulo AS livro_titulo,
    l.editora AS livro_editora,
    l.edicao AS livro_edicao,
    l.ano_publicacao AS livro_ano_publicacao,
    l.valor AS livro_valor,
    group_concat(asn.descricao ORDER BY asn.descricao ASC separator ', ') AS livro_assuntos
FROM
    autor a
JOIN
    livro_autor la ON a.codau = la.codau
JOIN
    livro l ON la.codl = l.codl
LEFT JOIN
    livro_assunto las ON l.codl = las.codl
left join
    assunto asn ON las.codas = asn.codas
GROUP BY
    a.nome, l.titulo, l.editora, l.edicao, l.ano_publicacao, l.valor;
