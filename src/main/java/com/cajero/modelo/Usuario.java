package com.cajero.modelo;

import java.util.concurrent.ThreadLocalRandom;

public class Usuario {
    private String Id;
    public String nombre;
    public String apellido;
    public String telefono;
    public String username;
    private String password;
    private String rol; // [admin, usuario, guest]
    private Cuenta cuenta;

    public Usuario(String nombre, String apellido, String telefono, String username, String password, Boolean guest) {
        this.Id = getAlphaNumericString();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.rol = guest ? "guest" : "usuario";
    }

    private static String getAlphaNumericString() {
        int numero = ThreadLocalRandom.current().nextInt(10000, 100000);
        return String.valueOf(numero);
    }

    public String getId() {
        return Id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta nuevaCuenta) {
        this.cuenta = nuevaCuenta;
    }

    public String getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.apellido + " " + this.telefono + " " + this.rol + " " + this.username;
    }

}
