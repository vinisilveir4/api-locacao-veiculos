create
database db_locacao_veiculos;

use db_locacao_veiculos;

create table usuario
(
    id            bigint primary key auto_increment,
    nome          varchar(255)        not null,
    sobrenome     varchar(255)        not null,
    numero        varchar(11),
    endereco      varchar(255)        not null,
    cep           varchar(8),
    cpf           varchar(11) unique  not null,
    email         varchar(255) unique not null,
    senha         varchar(255)        not null,
    data_criacao  datetime            not null,
    atualizado_em datetime
);

create table locacao
(
    id           bigint primary key auto_increment,
    usuario_id   bigint,
    veiculo_id   bigint,
    data_inicio  datetime not null,
    data_fim     datetime not null,
    valor        decimal(10, 2),
    status_      enum('PENDENTE', 'APROVADA', 'EM_ANDAMENTO', 'FINALIZADA', 'CANCELADA') not null,
    data_criacao datetime not null,
    foreign key (usuario_id) references usuario (id),
    foreign key (veiculo_id) references veiculo (id)
);

create table veiculo
(
    id             bigint primary key auto_increment,
    modelo         varchar(255) not null,
    marca          varchar(255) not null,
    ano_fabricacao date         not null,
    placa          varchar(7)   not null unique,
    status_        enum('DISPONIVEL', 'LOCADO', 'MANUTENCAO') not null,
    valor_diario   decimal(10, 2)
);

create table permissao
(
    id   bigint primary key auto_increment,
    nome varchar(255) not null
);

create table permissoes_usuario
(
    usuario_id   bigint not null,
    permissao_id bigint not null,
    primary key (usuario_id, permissao_id),
    foreign key (usuario_id) references usuario (id),
    foreign key (permissao_id) references permissao (id)
);

insert into permissao (nome)
values ('ADMIN'),
       ('USUARIO'),
       ('VENDEDOR');

insert into usuario (nome, sobrenome, numero, endereco, cpf, email, senha, data_criacao, atualizado_em)
values ('Vinícius', 'Silveira', '51980111111', 'Rua barão 70', '00000000000', 'adminLV@gmail.com',
        '$2a$10$hCBEWkpzeMplOJwqjLbL1OuGvaGheG5QUM5ujzCfy1TUunyazLvMC', '2024-09-27 14:21:53', '2024-09-27 14:21:53');

insert into permissoes_usuario (usuario_id, permissao_id) value (1, 1). (1, 2), (1, 3);

insert into usuario (nome, sobrenome, numero, endereco, cpf, email, senha, data_criacao, atualizado_em)
values ('Matheus', 'Henrique', '51996106221', 'Rua teste, 123 - Bairro testando', '84806831042', 'teste123@gmail.com',
        '$10$df9EaG/9tvV5ojXSaWG2POQVlEmliFWLDJ9krRWjy3Z0i56ieZv/q', '2024-10-07 15:37:38', '2024-10-07 15:37:38');

insert into permissoes_usuario (usuario_id, permissao_id) value (2, 3);

INSERT INTO carros (modelo, marca, ano_fabricacao, placa, valor_diario, status_)
VALUES ('corolla', 'toyota', '2015-07-11', 'FGT9P34', 150.00, 'DISPONIVEL'),
       ('gol', 'volkswagen', '2012-03-18', 'KLM2T90', 100.75, 'DISPONIVEL');