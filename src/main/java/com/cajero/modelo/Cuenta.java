package com.cajero.modelo;

import java.util.ArrayList;

public class Cuenta {
    private Double monto = 0.0;
    private Usuario usuario;
    private ArrayList<String> movimientos = new ArrayList<String>();

    public Cuenta(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getMonto() {
        return monto;
    }

    public void addMonto(Double monto, String motivo) {
        this.monto += monto;
        this.movimientos.add("+ $" + monto + " - " + motivo);
    }

    public void substractMonto(Double monto, String motivo) {
        if (this.monto <= monto) {
            // TODO: mostrar el mensaje de error
            System.out.println("Monto insuficiente");
            return;
        }

        this.monto -= monto;
        this.movimientos.add("- $" + monto + " - " + motivo);
    }

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
