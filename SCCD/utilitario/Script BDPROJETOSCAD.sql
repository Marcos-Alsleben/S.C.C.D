CREATE DATABASE BDPROJETOSCAD;


CREATE USER 'carton'@'%' IDENTIFIED BY '2574';

GRANT ALL ON *.* TO 'carton'@'%' WITH GRANT OPTION;


flush privileges;


USE BDPROJETOSCAD;

/***** TABELA DESENHOS *****/
CREATE TABLE tb_desenhos (
  id int auto_increment primary key,
  faca int(7),
  comprimento float (5,2),
  largura float (5,2),
  altura float (5,2),
  colagem varchar (30),
  abas varchar (30),
  berco varchar(30),
  promocional varchar(30),
  operador varchar(100),
  datahora varchar (255)
);
/*****************/

/***** TABELA EMPRESA *****/
CREATE TABLE tb_empresa (
  id int auto_increment primary key,
  nome varchar(50),
  endereco varchar (50),
  telefone varchar (20),
  observacao varchar(200)
);
/*****************/

/***** TABELA CONTA *****/
CREATE TABLE tb_conta (
  id int auto_increment primary key,
  nome varchar(35),
  senha varchar (10),
  previlegio varchar (20)
);
/*****************/