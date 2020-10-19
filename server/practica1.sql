CREATE DATABASE distribuida_practica1;
USE distribuida_practica1;

CREATE TABLE corredor(
    pk_corredor INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(32) NOT NULL UNIQUE,
    passhash BINARY(32) NOT NULL,
    estado VARCHAR(32) NOT NULL,
    genero VARCHAR(32) NOT NULL,
    edad INTEGER NOT NULL
);

CREATE TABLE carrera(
    pk_carrera INTEGER AUTO_INCREMENT PRIMARY KEY,
    corredor VARCHAR(32) NOT NULL,
    tipo VARCHAR(32) NOT NULL,
    tiempo TIME(2) NOT NULL,
    distancia INTEGER NOT NULL,
    fecha DATE NOT NULL
);

CREATE USER 'distribuida'@'localhost' IDENTIFIED BY '12345';
GRANT ALL PRIVILEGES ON distribuida_practica1.* TO 'distribuida'@'localhost';
FLUSH PRIVILEGES;
