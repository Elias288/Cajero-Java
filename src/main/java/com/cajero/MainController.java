package com.cajero;

import java.io.IOException;

import com.cajero.manager.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Button btn_verPerfil;

    @FXML
    private Button btc_transaccionar;

    @FXML
    private Button btc_closeSession;

    @FXML
    private void switchToPerfil() throws IOException {
        App.setRoot("viewProfile");
    }

    @FXML
    private void closeSession() throws IOException {
        SessionManager.getInstance().cerrarSesion();
        App.setRoot("primary");
    }
}
