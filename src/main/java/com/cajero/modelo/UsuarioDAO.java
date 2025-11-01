package com.cajero.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    public static void storeUser(Connection conn, Usuario usuario, String contraseña) throws SQLException {
        String contraseñaEncriptada = BCrypt.hashpw(contraseña, BCrypt.gensalt(12));

        String sql = "INSERT INTO Usuario (id, nombre, apellido, telefono, username, password, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario.getId());
        stmt.setString(2, usuario.nombre);
        stmt.setString(3, usuario.apellido);
        stmt.setString(4, usuario.telefono);
        stmt.setString(5, usuario.username);
        stmt.setString(6, contraseñaEncriptada);
        stmt.setString(7, usuario.getRol());

        ResultSet rs = stmt.executeQuery();

        rs.close();
        stmt.close();

        CuentaDAO.storeCuenta(conn, usuario.getCuenta());
    }

    public static Usuario getUsuarioByUsername(Connection conn, String username) throws SQLException {
        Usuario user = null;
        String sql = "SELECT u.id, u.nombre, u.apellido, u.telefono, u.username, u.password, u.rol FROM `Usuario` as u WHERE username = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            user = new Usuario(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("username"),
                    rs.getString("rol"),
                    rs.getString("password"));
        }

        rs.close();
        stmt.close();

        return user;
    }

    public static Usuario login(Connection conn, String username, String password) throws SQLException {
        Usuario user = getUsuarioByUsername(conn, username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            user.setCuenta(CuentaDAO.getCuentaByUsuarioId(conn, user.getId()));
            return user;
        }
        return null;
    }
}
