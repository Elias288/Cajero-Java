package com.cajero.manager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cajero.modelo.Cuenta;

import com.cajero.modelo.CuentaDAO;

public class TransactionManager {
    public static Connection conn = ConexionManager.getInstance().getConnection();

    public static void ingreso(Cuenta cuenta, BigDecimal monto, String motivo, String idCuentaBen)
            throws ArithmeticException {
        if (conn != null)
            CuentaDAO.storeMovimiento(conn, monto, motivo, cuenta, idCuentaBen);

        cuenta.addMonto(monto, motivo);
    }

    public static void egreso(Cuenta cuenta, BigDecimal monto, String motivo, String idCuentaBen)
            throws ArithmeticException {
        if (conn != null)
            CuentaDAO.storeMovimiento(conn, monto.negate(), motivo, cuenta, idCuentaBen);

        cuenta.substractMonto(monto, motivo);
    }

    public static void sendAmount(Cuenta cuentaOrdenante, BigDecimal monto, String motivo,
            String idCuentaBeneficiaria) throws SQLException {
        if (conn != null) {
            /*
             * Funcionamiento de una transacción
             * - Paso 1: Actualizar el monto de la cuenta ordenante
             * - Paso 2: Actualizar el monto de la cuenta beneficiaria
             * - Paso 3: Agregar el movimiento de la transacción
             */
            try {
                conn.setAutoCommit(false);
                try (
                        PreparedStatement debitar = conn.prepareStatement(
                                "UPDATE Cuenta SET monto = monto - ? WHERE id = ?;");
                        PreparedStatement acreditar = conn.prepareStatement(
                                "UPDATE Cuenta SET monto = monto + ? WHERE id = ?;");
                        PreparedStatement movimiento = conn.prepareStatement(
                                "INSERT INTO Movimiento (idCuentaBen, idCuentaOrd, monto, motivo) VALUES (?, ?, ?, ?);");
                        PreparedStatement verificar = conn.prepareStatement(
                                "SELECT monto FROM Cuenta WHERE id = ?");) {

                    verificar.setString(1, cuentaOrdenante.getId());
                    ResultSet rs = verificar.executeQuery();
                    if (!rs.next())
                        throw new SQLException("Cuenta ordenante no encontrada");
                    BigDecimal saldoActual = rs.getBigDecimal("monto");
                    if (saldoActual.compareTo(monto) <= 0)
                        throw new SQLException("Saldo insuficiente");

                    // --- Debitar y acreditar ---
                    debitar.setBigDecimal(1, monto);
                    debitar.setString(2, cuentaOrdenante.getId());
                    int filasDebito = debitar.executeUpdate();
                    if (filasDebito == 0)
                        throw new SQLException("No se pudo debitar, cuenta ordenante no existe.");

                    acreditar.setBigDecimal(1, monto);
                    acreditar.setString(2, idCuentaBeneficiaria);
                    int filasCredito = acreditar.executeUpdate();
                    if (filasCredito == 0)
                        throw new SQLException("No se pudo acreditar, cuenta beneficiaria no existe.");

                    movimiento.setString(1, cuentaOrdenante.getId());
                    movimiento.setString(2, idCuentaBeneficiaria);
                    movimiento.setBigDecimal(3, monto);
                    movimiento.setString(4, motivo);
                    movimiento.executeQuery();

                    conn.commit();
                    rs.close();
                    debitar.close();
                    acreditar.close();
                    movimiento.close();
                } catch (SQLException e) {
                    conn.rollback();
                    throw new SQLException("Error, se revierte la transacción: " + e.getMessage());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException(e.getMessage());
            }
        } else {
            try {
                cuentaOrdenante.substractMonto(monto, motivo);
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
        }
    }
}
