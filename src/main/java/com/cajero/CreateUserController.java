package com.cajero;

import java.io.IOException;

import com.cajero.manager.SessionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateUserController {
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
        Usuario nuevUsuario = new Usuario(txt_nombre.getText(), txt_apellido.getText(), txt_telefono.getText(),
                txt_username.getText(), txt_pass.getText(), false);
        nuevUsuario.setCuenta(new Cuenta(nuevUsuario));

        SessionManager.getInstance().setUsuarioActual(nuevUsuario);

        App.setRoot("main");
    }

    @FXML
    void cancelar() throws IOException {
        App.setRoot("primary");
    }
}
