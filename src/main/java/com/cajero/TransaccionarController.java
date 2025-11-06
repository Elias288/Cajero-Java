package com.cajero;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

import com.cajero.manager.ConexionManager;
import com.cajero.manager.SessionManager;
import com.cajero.manager.TransactionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class TransaccionarController {
    Usuario usuario = SessionManager.getInstance().getUsuarioActual();
    Connection conexion = ConexionManager.getInstance().initConexion();

    @FXML
    private Label lbl_monto;
    @FXML
    private TextArea txa_ver;
    @FXML
    private TextField txt_motivo;
    @FXML
    private TextField txt_account;
    @FXML
    private Button btn_ver;
    @FXML
    private Button btn_enviar;
    @FXML
    private Spinner<Double> spinner_amount;

    Double currentValue;

    @FXML
    private void initialize() throws IOException {
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 9999);
        valueFactory.setValue(1.0);
        spinner_amount.setValueFactory(valueFactory);

        if (usuario != null) {
            Cuenta cuenta = usuario.getCuenta();
            lbl_monto.setText("$" + cuenta.getMonto().toString());
        }

        if (conexion != null) {
            txt_account.setDisable(false);
        }

        /*
         * Se enlaza el estado del boton a la caja de texto, esto no permite manejar el
         * estado del boton de manera separada
         */
        btn_ver.disableProperty().bind(
                txt_motivo.textProperty().isEmpty());

        /*
         * Se crea un evento de escucha que no enlaza el estado del boton a la caja de
         * texto
         */
        txa_ver.textProperty().addListener((obs, old, nuevo) -> {
            btn_enviar.setDisable(nuevo.trim().isEmpty());
        });
    }

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void simular() throws IOException {
        txa_ver.setText("Se enviarán $" + spinner_amount.getValue() + " a la cuenta id:" + txt_account.getText()
                + " con el motivo: " + txt_motivo.getText());
    }

    @FXML
    private void transaccionar() {
        try {
            TransactionManager.sendAmount(
                    usuario.getCuenta(),
                    new BigDecimal(spinner_amount.getValue()),
                    txt_motivo.getText(),
                    txt_account.getText());
            App.setRoot("main");
        } catch (Exception e) {
            e.printStackTrace();
            txa_ver.setText("");

            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Se produjo un error");
            alerta.setContentText(e.getMessage() != null ? e.getMessage() : e.toString());
            alerta.showAndWait();
        }
    }
}
