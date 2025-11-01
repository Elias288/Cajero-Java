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

    private static ArrayList<String> getMovimientos(Connection conn, String cuentaId) throws SQLException {
        ArrayList<String> movimientos = new ArrayList<String>();
        String sql = "SELECT motivo, monto FROM `Movimiento` WHERE idCuenta = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cuentaId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            movimientos.add("+ $" + rs.getBigDecimal("monto") + " - " + rs.getString("motivo"));
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

    public static void storeMovimiento(Connection conn, BigDecimal monto, String motivo, Cuenta cuenta) {
        try {
            String sql = "INSERT INTO Movimiento (motivo, monto, idCuenta) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, motivo);
            stmt.setBigDecimal(2, monto);
            stmt.setString(3, cuenta.getId());

            ResultSet rs = stmt.executeQuery();

            rs.close();
            stmt.close();

            updateCuentaMonto(conn, cuenta, monto);

        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Se produjo un error");
            alerta.setContentText(e.getMessage() != null ? e.getMessage() : e.toString());
            alerta.showAndWait();
            return;
        }
    }
}
