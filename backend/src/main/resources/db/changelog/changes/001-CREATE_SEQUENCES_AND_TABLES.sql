-- Criação das sequences para Primary Keys, começando de 1 e incrementando por 1
CREATE SEQUENCE seq_autor START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_assunto START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_livro START WITH 1 INCREMENT BY 1;

-- Tabela Autor
CREATE TABLE IF NOT EXISTS Autor (
    CodAu INTEGER PRIMARY KEY,
    nome VARCHAR(40) NOT NULL
);

-- Tabela Assunto
CREATE TABLE IF NOT EXISTS Assunto (
    codAs INTEGER PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL
);

-- Tabela Livro
CREATE TABLE IF NOT EXISTS Livro (
    CodL INTEGER PRIMARY KEY,
    Titulo VARCHAR(40) NOT NULL,
    Editora VARCHAR(40) NOT NULL,
    Edicao INTEGER NOT NULL,
    Ano_Publicacao VARCHAR(4) NOT NULL,
    Valor DECIMAL(10, 2) NOT NULL
);

-- Tabela Livro_Autor (Relacionamento entre Livro e Autor)
CREATE TABLE Livro_Autor (
    CodL INTEGER NOT NULL,
    CodAu INTEGER NOT NULL,
    PRIMARY KEY (CodL, CodAu),
    FOREIGN KEY (CodL) REFERENCES Livro(CodL),
    FOREIGN KEY (CodAu) REFERENCES Autor(CodAu)
);

-- Tabela Livro_Assunto (Relacionamento entre Livro e Assunto)

CREATE TABLE Livro_Assunto (
    CodL INTEGER NOT NULL,
    CodAs INTEGER NOT NULL,
    PRIMARY KEY (CodL, CodAs),
    FOREIGN KEY (CodL) REFERENCES Livro(CodL),
    FOREIGN KEY (CodAs) REFERENCES Assunto(CodAs)
);

