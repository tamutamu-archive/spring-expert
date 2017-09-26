create table estilo (
	codigo bigint(20) primary key auto_increment, 
	nome varchar(50) not null
)engine=innodb default charset=utf8;

create table cerveja (
	codigo bigint(20) primary key auto_increment,
	sku varchar(50) not null, 
	nome varchar(80) not null, 
	descricao text not null,
	foto varchar(50), 
	valor decimal(10,2) not null, 
	teor_alcoolico decimal(10,2) not null,
	comissao decimal(10, 2) not null, 
	sabor varchar(50) not null, 
	origem varchar(50) not null, 
	estilo bigint(20) not null,
	quantidade_estoque integer not null default '0',
	foreign key (estilo) references estilo(codigo)
)engine=innodb default charset=utf8;

insert into estilo(nome) values ('Amber Lager');
insert into estilo(nome) values ('Dark Lager');
insert into estilo(nome) values ('Pale Lager');
insert into estilo(nome) values ('Pilsener');
insert into estilo(nome) values ('Pale Ale');