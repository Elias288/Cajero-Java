package com.cajero;

import java.io.IOException;

import com.cajero.manager.SessionManager;
import com.cajero.modelo.Cuenta;
import com.cajero.modelo.Usuario;

import javafx.fxml.FXML;

public class LoginController {

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
