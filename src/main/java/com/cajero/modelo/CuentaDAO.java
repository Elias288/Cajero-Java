package com.cajero.modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CuentaDAO {
    public static void storeCuenta(Connection conn, Cuenta cuenta) throws SQLException {
        String sql = "INSERT INTO Cuenta (id, monto, idUsuario) VALUES (?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cuenta.getId());
        stmt.setBigDecimal(2, cuenta.getMonto());
        stmt.setString(3, cuenta.getUsuarioId());

        ResultSet rs = stmt.executeQuery();

        rs.close();
        stmt.close();
    }

    public static Cuenta getCuentaByUsuarioId(Connection conn, String usuarioId) throws SQLException {
        Cuenta cuenta = null;
        String sql = "SELECT c.id, c.monto FROM `Cuenta` as c WHERE c.idUsuario = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuarioId);

        ResultSet rs = stmt.executeQuery();

        ArrayList<String> lista = new ArrayList<>();
        if (rs.next()) {
            cuenta = new Cuenta(
                    rs.getString("id"),
                    rs.getBigDecimal("monto"),
                    usuarioId,
                    lista);

            ArrayList<String> movimientos = getMovimientos(conn, cuenta.getId());
            cuenta.setMovimientos(movimientos);
        }

        rs.close();
        stmt.close();
        return cuenta;
    }

    public static ArrayList<String> getMovimientos(Connection conn, String cuentaId) throws SQLException {
        ArrayList<String> movimientos = new ArrayList<String>();
        PreparedStatement ingresos = conn.prepareStatement(
                "SELECT motivo, monto FROM `Movimiento` WHERE idCuentaBen = ?");
        PreparedStatement egresos = conn.prepareStatement(
                "SELECT motivo, monto FROM `Movimiento` WHERE idCuentaOrd = ?");

        ingresos.setString(1, cuentaId);
        ResultSet rsIngresos = ingresos.executeQuery();
        while (rsIngresos.next()) {
            movimientos.add("+ $" + rsIngresos.getBigDecimal("monto") + " - " + rsIngresos.getString("motivo"));
        }

        egresos.setString(1, cuentaId);
        ResultSet rsEgresos = egresos.executeQuery();
        while (rsEgresos.next()) {
            movimientos.add("- $" + rsEgresos.getBigDecimal("monto") + " - " + rsEgresos.getString("motivo"));
        }

        return movimientos;
    }

    private static void updateCuentaMonto(Connection conn, Cuenta cuenta, BigDecimal monto) throws SQLException {

        BigDecimal montoAcutal = cuenta.getMonto();
        BigDecimal nuevoMonto = montoAcutal.add(monto);

        String sql = "UPDATE Cuenta SET monto = ? WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBigDecimal(1, nuevoMonto);
        stmt.setString(2, cuenta.getId());

        ResultSet rs = stmt.executeQuery();

        rs.close();
        stmt.close();
    }

    public static void storeMovimiento(Connection conn, BigDecimal monto, String motivo, Cuenta cuentaOrd,
            String idCuentaBen) {
        try {
            String sql = "INSERT INTO Movimiento (motivo, monto, idCuentaOrd, idCuentaBen) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, motivo);
            stmt.setBigDecimal(2, monto);
            stmt.setString(3, cuentaOrd.getId());
            stmt.setString(4, idCuentaBen);

            ResultSet rs = stmt.executeQuery();

            rs.close();
            stmt.close();

            updateCuentaMonto(conn, cuentaOrd, monto);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Se produjo un error");
            alerta.setContentText(e.getMessage() != null ? e.getMessage() : e.toString());
            alerta.showAndWait();
            return;
        }
    }
}
