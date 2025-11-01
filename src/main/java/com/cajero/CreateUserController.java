package com.cajero;

import java.io.IOException;
import java.sql.Connection;

import com.cajero.manager.ConexionManager;
import com.cajero.manager.SessionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;
import com.cajero.modelo.UsuarioDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateUserController {
    Connection conexion = ConexionManager.getInstance().getConnection();

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_pass;

    @FXML
    void createUser() throws IOException {
        Usuario nuevUsuario = new Usuario(
                txt_nombre.getText(),
                txt_apellido.getText(),
                txt_telefono.getText(),
                txt_username.getText().toLowerCase(),
                "usuario",
                txt_pass.getText());
        nuevUsuario.setCuenta(new Cuenta(nuevUsuario.getId()));

        /* Si hay conexión con la base de datos, registra el usuario */
        if (conexion != null) {
            try {
                UsuarioDAO.storeUser(conexion, nuevUsuario, txt_pass.getText());
            } catch (Exception e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Se produjo un error");
                alerta.setContentText(e.getMessage() != null ? e.getMessage() : e.toString());
                alerta.showAndWait();
                return;
            }
        }

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Exitoso");
        alerta.setContentText("Usuario '" + nuevUsuario.nombre + " " + nuevUsuario.apellido + "' creado con exito");
        alerta.showAndWait();
        SessionManager.getInstance().setUsuarioActual(nuevUsuario);

        App.setRoot("main");
    }

    @FXML
    void cancelar() throws IOException {
        App.setRoot("primary");
    }
}
