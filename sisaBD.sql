CREATE DATABASE IF NOT EXISTS sisadb;

-- Crear la tabla de Usuarios
CREATE TABLE Usuarios
(
    ID              serial PRIMARY KEY,
    Nombre          TEXT,
    Apellido        TEXT,
    Tipo_de_Usuario TEXT,
    Email           TEXT UNIQUE,
    celular         TEXT,
    password        TEXT,
    direccion       TEXT,
    genero          VARCHAR(20),
    fecha_registro  DATE,
    foto            BYTEA
);


-- Crear la tabla de Cursos
CREATE TABLE Cursos
(
    ID               serial PRIMARY KEY,
    Nombre_del_Curso TEXT,
    Descripcion      TEXT,
    ciclo            varchar(255),
    codigo           varchar(6),
    url_foto         TEXT
);

-- Crear la tabla de Clases
CREATE TABLE Clases
(
    ID                   serial PRIMARY KEY,
    ID_del_Curso         INT REFERENCES Cursos (ID),
    Fecha                DATE,
    Hora_de_Inicio       TIME,
    Hora_de_Finalizacion TIME,
    Ubicacion            TEXT,
    descripcion          TEXT
);

-- Crear la tabla de Asistencia
CREATE TABLE Asistencia
(
    ID                  serial PRIMARY KEY,
    ID_del_Usuario      INT REFERENCES Usuarios (ID),
    ID_de_la_Clase      INT REFERENCES Clases (ID),
    Fecha_de_Asistencia DATE,
    Presente            BOOLEAN,
    Tardanza            BOOLEAN,
    Falta               BOOLEAN
);

-- Crear la tabla de Docentes
CREATE TABLE Docentes
(
    ID              serial PRIMARY KEY,
    ID_del_Usuario  INT REFERENCES Usuarios (ID),
    Especializacion TEXT
);

-- Crear la tabla intermedia entre Docentes y Cursos
CREATE TABLE CursosDocentes
(
    ID             serial PRIMARY KEY,
    ID_del_Curso   INT REFERENCES Cursos (ID),
    ID_del_Docente INT REFERENCES Docentes (ID),
    UNIQUE (ID_del_Curso, ID_del_Docente)
);


-- Crear la tabla de Auditoria
CREATE TABLE Auditoria
(
    ID                   serial PRIMARY KEY,
    ID_del_Usuario       INT REFERENCES Usuarios (ID),
    Tabla_Afectada       TEXT,
    ID_Registro_Afectado INT,
    Accion               TEXT,
    Fecha_Accion         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--
-- -- Trigger para INSERT (Crear)
-- CREATE
--     OR REPLACE FUNCTION auditoria_asistencia_insert()
--     RETURNS TRIGGER AS
-- $$
-- BEGIN
--     INSERT INTO Auditoria (ID_del_Usuario, Tabla_Afectada, ID_Registro_Afectado, Accion)
--     VALUES (CURRENT_USER, 'Asistencia', NEW.ID, 'INSERT');
--     RETURN NEW;
-- END;
-- $$
--     LANGUAGE plpgsql;
--
-- CREATE TRIGGER auditoria_asistencia_insert
--     AFTER INSERT
--     ON Asistencia
--     FOR EACH ROW
-- EXECUTE FUNCTION auditoria_asistencia_insert();
--
-- -- Trigger para UPDATE (Actualizar)
-- CREATE
--     OR REPLACE FUNCTION auditoria_asistencia_update()
--     RETURNS TRIGGER AS
-- $$
-- BEGIN
--     INSERT INTO Auditoria (ID_del_Usuario, Tabla_Afectada, ID_Registro_Afectado, Accion)
--     VALUES (CURRENT_USER, 'Asistencia', NEW.ID, 'UPDATE');
--     RETURN NEW;
-- END;
-- $$
--     LANGUAGE plpgsql;
--
-- CREATE TRIGGER auditoria_asistencia_update
--     AFTER UPDATE
--     ON Asistencia
--     FOR EACH ROW
-- EXECUTE FUNCTION auditoria_asistencia_update();
--
-- -- Trigger para DELETE (Eliminar)
-- CREATE
--     OR REPLACE FUNCTION auditoria_asistencia_delete()
--     RETURNS TRIGGER AS
-- $$
-- BEGIN
--     INSERT INTO Auditoria (ID_del_Usuario, Tabla_Afectada, ID_Registro_Afectado, Accion)
--     VALUES (CURRENT_USER, 'Asistencia', OLD.ID, 'DELETE');
--     RETURN OLD;
-- END;
-- $$
--     LANGUAGE plpgsql;
--
-- CREATE TRIGGER auditoria_asistencia_delete
--     AFTER DELETE
--     ON Asistencia
--     FOR EACH ROW
-- EXECUTE FUNCTION auditoria_asistencia_delete();
