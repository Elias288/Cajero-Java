package com.cajero.manager;

import com.cajero.modelo.Cuenta;

public class TransactionManager {

    public static void sendAmount(Cuenta cuentaActual, Double amount, String motivo, String idAccount)
            throws Exception {
        cuentaActual.substractMonto(amount, motivo);
    }
}
