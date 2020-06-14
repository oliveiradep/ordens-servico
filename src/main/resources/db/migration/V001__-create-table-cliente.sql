CREATE TABLE cliente (
	id bigint not null auto_increment,
	nome VARCHAR(100) not null,
	email VARCHAR(50) not null,
	telefone VARCHAR(20) not null,

	primary key (id)
);