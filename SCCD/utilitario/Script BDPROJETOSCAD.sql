CREATE DATABASE BDPROJETOSCAD;


CREATE USER 'carton'@'%' IDENTIFIED BY '2574';

GRANT ALL ON *.* TO 'carton'@'%' WITH GRANT OPTION;


flush privileges;


USE BDPROJETOSCAD;

/***** TABELA DESENHOS *****/
CREATE TABLE tb_desenhos (
id int primary key auto_increment unique,
faca int unique,
comprimento float (5.2),
largura float (5.2),
altura float (5.2),
colagem varchar (30),
abas varchar (30),
berco varchar(30),
promocional varchar(30),
cadastradopor varchar(100),
datahora TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
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

/***** TABELA ABAS LATERAL *****/
CREATE TABLE tb_abaslateral (
id int auto_increment primary key,
tipo varchar(50),
cadastradopor varchar(100),
datahora TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
);
/*****************/

/***** TABELA ABAS FUNDO AUTOMATICO *****/
CREATE TABLE tb_abasfundoautomatico (
id int auto_increment primary key,
tipo varchar(35),
cadastradopor varchar(100),
datahora TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
);
/*****************/

/***** TABELA ABAS OUTROS *****/
CREATE TABLE tb_abasoutros (
id int auto_increment primary key,
tipo varchar(35),
cadastradopor varchar(100),
datahora TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
);
/*****************/

/***** TABELA CAMINHO PARA VISUALIZAÇÃO DA ARTE *****/
CREATE TABLE tb_caminhoarte (
id int auto_increment primary key,
endereco varchar(35),
cadastradopor varchar(100),
datahora TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
);
/*****************/
