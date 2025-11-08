package com.cajero.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionManager {
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
        if (conexion == null) {
            try (InputStream input = ConexionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
                Properties prop = new Properties();
                if (input == null)
                    throw new RuntimeException("No se configuró db.properties");
                prop.load(input);

                String url = prop.getProperty("db.url");
                String usuario = prop.getProperty("db.user");
                String contraseña = prop.getProperty("db.password");

                this.conexion = DriverManager.getConnection(url, usuario, contraseña);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                this.conexion = null;
            }
        }

        return this.conexion;
    }
}
