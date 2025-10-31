package com.cajero;

import java.io.IOException;
import java.sql.Connection;

import com.cajero.manager.ConexionManager;
import com.cajero.manager.SessionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    Connection conexion = ConexionManager.getInstance().getConnection();

    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_pass;
    @FXML
    private Label lbl_connection;
    @FXML
    private Button btn_ingresar;

    @FXML
    private void initialize() {
        if (conexion == null) {
            btn_ingresar.setDisable(true);
            txt_pass.setDisable(true);
            txt_username.setDisable(true);
            lbl_connection.setText("❌ No conectado a la base de datos");
            return;
        }

        lbl_connection.setText("✅ Base de datos conectada");
    }

    @FXML
    private void swichToMainGuest() throws IOException {
        Usuario usuarioGuest = new Usuario("Guest", "", "+555555555", "guest", "", true);
        Cuenta cuentaGuest = new Cuenta(usuarioGuest);
        cuentaGuest.addMonto(100.0, "Carga inicial");
        cuentaGuest.addMonto(50.0, "Segunda carga");
        cuentaGuest.substractMonto(25.0, "Pago de servicio");
        usuarioGuest.setCuenta(cuentaGuest);

        SessionManager.getInstance().setUsuarioActual(usuarioGuest);

        App.setRoot("main");
    }
}
