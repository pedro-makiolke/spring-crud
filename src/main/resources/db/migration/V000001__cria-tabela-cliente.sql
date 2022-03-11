CREATE TABLE cliente(
    id serial primary key not null,
    nome varchar(60) not null,
    email varchar(255),
    telefone varchar(20)
)