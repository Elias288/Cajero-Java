package com.cajero;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import com.cajero.manager.ConexionManager;
import com.cajero.modelo.Usuario;
import com.cajero.modelo.UsuarioDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VerCuentasController {
    Connection conexion = ConexionManager.getInstance().getConnection();
    @FXML
    private Button btn_volver;

    @FXML
    private TableView<Usuario> tbl_cuentas;
    @FXML
    private TableColumn<Usuario, String> col_id;
    @FXML
    private TableColumn<Usuario, String> col_nombre;
    @FXML
    private TableColumn<Usuario, String> col_telefono;
    @FXML
    private TableColumn<Usuario, String> col_username;
    @FXML
    private TableColumn<Usuario, String> col_rol;
    @FXML
    private TableColumn<Usuario, String> col_monto;

    private ObservableList<Usuario> listaUsuarios;

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void initialize() throws IOException {
        try {
            ArrayList<Usuario> usuarios = UsuarioDAO.getAllUsuarios(conexion);

            col_id.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(
                            data.getValue().getCuenta().getId()));
            col_nombre.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(
                            data.getValue().nombre + " " + data.getValue().apellido));
            col_monto.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty("$ " +
                            data.getValue().getCuenta().getMonto().toString()));
            col_telefono.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(
                            data.getValue().telefono));
            col_rol.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(
                            data.getValue().getRol()));
            col_username.setCellValueFactory(
                    data -> new javafx.beans.property.SimpleStringProperty(
                            data.getValue().username));

            listaUsuarios = FXCollections.observableArrayList(usuarios);
            tbl_cuentas.setItems(listaUsuarios);

        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("Error al cargar los usuarios");
            alerta.showAndWait();
        }
    }
}
