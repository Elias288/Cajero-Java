CREATE TABLE Usuario (
    id varchar(5) PRIMARY KEY,
    nombre varchar(100),
    apellido VARCHAR(100),
    telefono VARCHAR(100) UNIQUE,
    username VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    rol VARCHAR(20)
);

CREATE TABLE Cuenta (
    id VARCHAR(5) PRIMARY KEY,
    monto DECIMAL(5),
    idUsuario VARCHAR(5),
    Foreign Key (idUsuario) REFERENCES Usuario(id)
);

CREATE TABLE Movimiento (
    motivo VARCHAR(200),
    monto DECIMAL(5),
    idCuenta VARCHAR(5),
    Foreign Key (idCuenta) REFERENCES Cuenta(id)
)