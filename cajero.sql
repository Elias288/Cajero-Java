CREATE TABLE Usuario (
    id varchar(5) PRIMARY KEY,
    nombre varchar(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20)
);

CREATE TABLE Cuenta (
    id VARCHAR(5) PRIMARY KEY,
    monto DECIMAL(5) NOT NULL,
    idUsuario VARCHAR(5) NOT NULL,
    Foreign Key (idUsuario) REFERENCES Usuario(id)
);

CREATE TABLE Movimiento (
    motivo VARCHAR(200) NOT NULL,
    monto DECIMAL(5) NOT NULL,
    idCuenta VARCHAR(5) NOT NULL,
    Foreign Key (idCuenta) REFERENCES Cuenta(id)
)