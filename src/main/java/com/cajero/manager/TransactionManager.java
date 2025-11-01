package com.cajero.manager;

import java.math.BigDecimal;
import java.sql.Connection;

import com.cajero.modelo.Cuenta;
import com.cajero.modelo.CuentaDAO;

public class TransactionManager {
    public static Connection conn = ConexionManager.getInstance().getConnection();

    public static void ingreso(Cuenta cuenta, BigDecimal monto, String motivo) throws ArithmeticException {
        if (conn != null)
            CuentaDAO.storeMovimiento(conn, monto, motivo, cuenta);

        cuenta.addMonto(monto, motivo);
    }

    public static void egreso(Cuenta cuenta, BigDecimal monto, String motivo) throws ArithmeticException {
        if (conn != null)
            CuentaDAO.storeMovimiento(conn, monto.negate(), motivo, cuenta);

        cuenta.substractMonto(monto, motivo);
    }

    public static void sendAmount(Cuenta cuentaActual, BigDecimal amount, String motivo, String idAccount)
            throws Exception {
        cuentaActual.substractMonto(amount, motivo);
    }
}
