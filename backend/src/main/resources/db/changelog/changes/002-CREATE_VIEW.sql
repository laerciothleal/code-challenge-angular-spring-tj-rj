CREATE OR replace VIEW vw_livros_por_autor AS
SELECT
    A.Nome AS autor_nome,
    L.Titulo AS livro_titulo,
    L.Editora AS livro_editora,
    L.Edicao AS livro_edicao,
    L.Ano_publicacao AS livro_ano_publicacao,
    L.Valor AS livro_valor,
    GROUP_CONCAT(ASn.Descricao ORDER BY ASn.Descricao ASC separator ', ') AS livro_ASsuntos
FROM
    Autor a
JOIN
    Livro_autor la ON a.Codau = la.Codau
JOIN
    Livro l ON la.Codl = l.Codl
Left join
    Livro_ASsunto lAS ON l.Codl = lAS.Codl
LEFT JOIN
    Assunto ASn ON lAS.CodAS = ASn.CodAS
GROUP BY
    A.Nome, l.Titulo, l.Editora, l.Edicao, l.Ano_publicacao, l.Valor;
