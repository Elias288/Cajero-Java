package com.cajero.modelo;

public class Usuario {
    private Integer Id;
    public String nombre;
    public String apellido;
    public String telefono;
    public String username;
    private String password;
    private String rol; // [admin, usuario, guest]
    private Cuenta cuentas;

    public Usuario(String nombre, String apellido, String telefono, String username, String password, Boolean guest) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.rol = guest ? "guest" : "usuario";
    }

    public Integer getId() {
        return Id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cuenta getCuenta() {
        return cuentas;
    }

    public void setCuenta(Cuenta nuevaCuenta) {
        this.cuentas = nuevaCuenta;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.apellido + " " + this.telefono + " " + this.rol + " " + this.username;
    }

}
