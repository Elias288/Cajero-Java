# Proyecto Cajero en Java

Proyecto simple en java, simulador de cajero automático con gestión de usuarios.

**index**
- [Desarrollo](#desarrollo)
    - [1. Inicio](#1-inicio)
    - [2. Creación de Usuario](#2-creación-de-usuario)
    - [3. Logueo](#3-logueo)
    - [4. Perfil](#4-perfil)
    - [5. Transaccionar](#5-transaccionar)
    - [6. Ver cuentas](#6-ver-cuentas)
- [Base de datos](#base-de-datos)
    - [Contenedor de MariaDB](#contenedor-mariadb)
    - [Diagrama](#diagrama)
    - [SQL](#sql)

## Desarrollo

### 1. Inicio

- [PrimaryController.java](src/main/java/com/cajero/PrimaryController.java)
- [primary.fxml](src/main/resources/com/cajero/primary.fxml)

![alt text](images/inicio.png)

### 2. Creación de usuario

- [CreateUserController.java](src/main/java/com/cajero/CreateUserController.java)
- [createUser.fxml](src/main/resources/com/cajero/createUser.fxml)

![alt text](images/crear_usuario.png)

> [!NOTE]
> Cuando no hay conexión a base de datos, la creación de usuario funciona solo en local

### 3. Logueo

Para la contraseña se utiliza un hash bcrypt(12) - [generador de hash online](https://bcrypt-generator.com/)

- [LoginController.java](src/main/java/com/cajero/LoginController.java)
- [login.fxml](src/main/resources/com/cajero/login.fxml)

![alt text](images/login.png)

> [!NOTE]
> Cuando no hay conexión a base de datos, la opción de logueo se desabilita y solo se permite entrar como invitado.

- [MainController.java](src/main/java/com/cajero/MainController.java)
- [main.fxml](src/main/resources/com/cajero/main.fxml)

![alt text](images/main.png)

> [!NOTE]
> El botón `Ver cuentas` solo se muestra para usuarios con rol `admin`.

### 4. Perfil

- [ViewProfileController](src/main/java/com/cajero/ViewProfileController.java)
- [viewProfile.fxml](src/main/resources/com/cajero/viewProfile.fxml)

![alt text](images/perfil.png)

### 5. Transaccionar

- [TransaccionarController.java](src/main/java/com/cajero/TransaccionarController.java)
- [transaccionar.fxml](src/main/resources/com/cajero/transaccionar.fxml)

![alt text](images/transaccionar.png)

> [!NOTE]
> Cuando no hay conexión a base de datos, solo se puede simular una transacción a la cuenta `11111`.

### 6. Ver cuentas

- [VerCuentrasController.java](src/main/java/com/cajero/VerCuentasController.java)
- [verCuentas](src/main/resources/com/cajero/verCuentas.fxml)

![alt text](images/verCuentas.png)

> [!NOTE]
> Solo para administradores (rol: admin)

## Base de datos

### Contenedor MariaDB

**Crear contenedor**

```sh
podman run -d \
    --name mariaDB \
  -e MARIADB_ROOT_PASSWORD=clave_root \
  -e MARIADB_DATABASE=cajero \
  -e MARIADB_USER=usuario \
  -e MARIADB_PASSWORD=clave_usuario \
  -p 3306:3306 \
  mariadb:noble
```

> [!NOTE]
> Se puede acceder a este contenedor mediante el `jdbc:mariadb://localhost:3306/cajero`

**Acceder a la base de datos en el contenedor**

```sh
podman exec -it mariaDB mariadb -u root -p
```

### Diagrama

![alt text](images/Cajero.png)

### SQL

[cajero.sql](./cajero.sql)

