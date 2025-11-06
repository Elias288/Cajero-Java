package com.cajero.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionManager {
    private static final String url = "jdbc:mariadb://localhost:3306/cajero";
    private static final String usuario = "root";
    private static final String contraseña = "pass123";
    private static ConexionManager instance;
    private Connection conexion;

    private ConexionManager() {
    }

    public static ConexionManager getInstance() {
        if (instance == null)
            instance = new ConexionManager();
        return instance;
    }

    public Connection getConnection() {
        return this.conexion;
    }

    public Connection initConexion() {
        try {
            this.conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            this.conexion = null;
        }

        return this.conexion;
    }
}
