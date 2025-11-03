package com.cajero;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

import com.cajero.manager.ConexionManager;
import com.cajero.manager.SessionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;
import com.cajero.modelo.UsuarioDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private void initialize() throws IOException {
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
        Usuario usuarioGuest = new Usuario("11111", "Guest", "", "55555555", "guest", "guest", "");
        usuarioGuest.setCuenta(new Cuenta(usuarioGuest.getId()));
        Cuenta cuentaGuest = usuarioGuest.getCuenta();
        cuentaGuest.addMonto(new BigDecimal("100.00"), "Carga inicial");
        cuentaGuest.addMonto(new BigDecimal("50.30"), "Segunda carga");
        cuentaGuest.substractMonto(new BigDecimal("25.00"), "Pago de servicio");

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Exito");
        alerta.setContentText("Login exitoso");
        alerta.showAndWait();
        SessionManager.getInstance().setUsuarioActual(usuarioGuest);

        App.setRoot("main");
    }

    @FXML
    private void login() throws IOException {
        try {
            Usuario loggedUsuario = UsuarioDAO.login(conexion, txt_username.getText().toLowerCase(),
                    txt_pass.getText());
            if (loggedUsuario == null)
                throw new Exception("Usuario o contraseña incorrectos");

            SessionManager.getInstance().setUsuarioActual(loggedUsuario);
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Exito");
            alerta.setContentText("Login exitoso");
            alerta.showAndWait();
            App.setRoot("main");

        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText(e.getMessage() != null ? e.getMessage() : e.toString());
            alerta.showAndWait();
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
