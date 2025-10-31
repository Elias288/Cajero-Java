package com.cajero;

import java.io.IOException;

import com.cajero.manager.SessionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewProfileController extends IOException {
    Usuario usuario = SessionManager.getInstance().getUsuarioActual();

    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_tel;
    @FXML
    private Label lbl_monto;
    @FXML
    private Label lbl_username;
    @FXML
    private Label lbl_rol;
    @FXML
    private Label lbl_id;

    @FXML
    private TableView<String> tabla_cuenta;
    @FXML
    private TableColumn<String, String> col_movimientos;

    private ObservableList<String> listaMovimientos;

    @FXML
    private void initialize() {
        Cuenta cuenta = usuario.getCuenta();

        if (usuario != null && cuenta != null) {
            lbl_id.setText(usuario.getId());
            lbl_nombre.setText(usuario.nombre + " " + usuario.apellido);
            lbl_tel.setText(usuario.telefono);
            lbl_username.setText(usuario.username);
            lbl_monto.setText("$ " + cuenta.getMonto().toString());
            lbl_rol.setText(usuario.getRol());

            col_movimientos
                    .setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));

            listaMovimientos = FXCollections.observableArrayList(cuenta.getMovimientos());

            tabla_cuenta.setItems(listaMovimientos);
        }
    }

    @FXML
    void switchToMain() throws IOException {
        App.setRoot("main");
    }
}