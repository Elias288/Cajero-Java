package com.cajero;

import java.io.IOException;
import java.sql.Connection;

import com.cajero.manager.ConexionManager;
import com.cajero.manager.SessionManager;
import com.cajero.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    Connection conexion = ConexionManager.getInstance().getConnection();
    Usuario usuario = SessionManager.getInstance().getUsuarioActual();

    @FXML
    private Button btn_verPerfil;

    @FXML
    private Button btc_transaccionar;

    @FXML
    private Button btc_closeSession;

    @FXML
    private Button btn_listarCuentas;

    @FXML
    private void initialize() throws IOException {
        Boolean showAdminOptions = false;
        if (conexion != null && usuario.getRol().equals("admin")) {
            showAdminOptions = true;
            btn_listarCuentas.setVisible(true);
            btn_listarCuentas.setDisable(false);
        }
        btn_listarCuentas.setManaged(showAdminOptions);
    }

    @FXML
    private void switchToPerfil() throws IOException {
        App.setRoot("viewProfile");
    }

    @FXML
    private void closeSession() throws IOException {
        SessionManager.getInstance().cerrarSesion();
        App.setRoot("primary");
    }

    @FXML
    private void switchToTransaccionar() throws IOException {
        App.setRoot("transaccionar");
    }

    @FXML
    private void switchToVerCuentas() throws IOException {
        App.setRoot("verCuentas");
    }
}
