package com.cajero;

import java.io.IOException;
import java.sql.Connection;

import com.cajero.manager.ConexionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {
    Connection conexion = ConexionManager.getInstance().getConnection();

    @FXML
    private Label lbl_connection;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchToCreateUser() throws IOException {
        App.setRoot("createUser");
    }

    @FXML
    private void initialize() {
        if (conexion == null) {
            lbl_connection.setText("❌ No se pudo conectar a la base de datos");
            return;
        }

        lbl_connection.setText("✅ Base de datos conectada");
    }
}
