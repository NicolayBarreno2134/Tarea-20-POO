CREATE TABLE participantes (
    id SERIAL PRIMARY KEY,
    cedula VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT NOT NULL CHECK (edad > 5),
    correo VARCHAR(150) UNIQUE NOT NULL,
    estado_civil VARCHAR(20) NOT NULL,
    jornada VARCHAR(20) NOT NULL, 
    categoria VARCHAR(50) NOT NULL, 
    observaciones TEXT
);
select * from partic