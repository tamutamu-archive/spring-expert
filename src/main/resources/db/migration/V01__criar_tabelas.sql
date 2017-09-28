DROP DATABASE IF EXISTS brewer;
CREATE DATABASE brewer DEFAULT CHARSET=utf8;
USE brewer;

CREATE TABLE estado (
  codigo bigint(20) NOT NULL,
  nome varchar(50) NOT NULL,
  sigla varchar(2) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cidade (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  estado bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  FOREIGN KEY (estado) REFERENCES estado (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cliente (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(80) NOT NULL,
  tipo_pessoa varchar(15) NOT NULL,
  cpf_cnpj varchar(30) NOT NULL,
  telefone varchar(20) DEFAULT NULL,
  email varchar(50) DEFAULT NULL,
  logradouro varchar(50) DEFAULT NULL,
  numero varchar(15) DEFAULT NULL,
  complemento varchar(20) DEFAULT NULL,
  cep varchar(15) DEFAULT NULL,
  cidade bigint(20) DEFAULT NULL,
  PRIMARY KEY (codigo),
  FOREIGN KEY (cidade) REFERENCES cidade (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE estilo (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cerveja (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  sku varchar(50) NOT NULL,
  nome varchar(80) NOT NULL,
  descricao text NOT NULL,
  valor decimal(10,2) NOT NULL,
  teor_alcoolico decimal(10,2) NOT NULL,
  comissao decimal(10,2) NOT NULL,
  sabor varchar(50) NOT NULL,
  origem varchar(50) NOT NULL,
  estilo bigint(20) NOT NULL,
  quantidade_estoque int(11) NOT NULL DEFAULT '0',
  foto varchar(100) DEFAULT NULL,
  content_type varchar(100) DEFAULT NULL,
  PRIMARY KEY (codigo),
  FOREIGN KEY (estilo) REFERENCES estilo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo (
  codigo bigint(20) NOT NULL,
  nome varchar(50) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
  codigo bigint(20) NOT NULL,
  nome varchar(50) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo_permissao (
  grupo bigint(20) NOT NULL,
  permissao bigint(20) NOT NULL,
  PRIMARY KEY (grupo,permissao),
  FOREIGN KEY (grupo) REFERENCES grupo (codigo),
  FOREIGN KEY (permissao) REFERENCES permissao (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  senha varchar(120) NOT NULL,
  ativo tinyint(1) DEFAULT '1',
  data_nascimento date DEFAULT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_grupo (
  usuario bigint(20) NOT NULL,
  grupo bigint(20) NOT NULL,
  PRIMARY KEY (usuario,grupo),
  FOREIGN KEY (usuario) REFERENCES usuario (codigo),
  FOREIGN KEY (grupo) REFERENCES grupo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;