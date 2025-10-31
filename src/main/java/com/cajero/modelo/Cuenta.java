package com.cajero.modelo;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cuenta {
    private String Id;
    private Double monto = 0.0;
    private Usuario usuario;
    private ArrayList<String> movimientos = new ArrayList<String>();

    public Cuenta(Usuario usuario) {
        this.Id = getAlphaNumericString();
        this.usuario = usuario;
    }

    private static String getAlphaNumericString() {
        int numero = ThreadLocalRandom.current().nextInt(10000, 100000);
        return String.valueOf(numero);
    }

    public Double getMonto() {
        return monto;
    }

    public void addMonto(Double monto, String motivo) {
        if (monto <= 0)
            throw new ArithmeticException("Monto invalido");

        if (this.monto + monto >= 9999)
            throw new ArithmeticException("No se pudo transaccionar, se supera el monto máximo");

        this.monto += monto;
        this.movimientos.add("+ $" + monto + " - " + motivo);
    }

    public void substractMonto(Double monto, String motivo) {
        if (monto <= 0)
            throw new ArithmeticException("Monto invalido");

        if (this.monto <= monto)
            throw new ArithmeticException("No se pudo transaccionar, monto insuficiente");

        this.monto -= monto;
        this.movimientos.add("- $" + monto + " - " + motivo);
    }

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getId() {
        return Id;
    }
}
