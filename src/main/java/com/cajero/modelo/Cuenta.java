package com.cajero.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cuenta {
    private String Id;
    private BigDecimal monto = new BigDecimal(0);
    private String usuarioId;
    private ArrayList<String> movimientos = new ArrayList<String>();

    public Cuenta(String usuarioId) {
        this.Id = getAlphaNumericString();
        this.usuarioId = usuarioId;
    }

    public Cuenta(String id, BigDecimal monto, String usuarioId, ArrayList<String> movimientos) {
        this.Id = id;
        this.monto = monto;
        this.usuarioId = usuarioId;
        this.movimientos = movimientos;
    }

    private static String getAlphaNumericString() {
        int numero = ThreadLocalRandom.current().nextInt(10000, 100000);
        return String.valueOf(numero);
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void addMonto(BigDecimal monto, String motivo) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0)
            throw new ArithmeticException("Monto invalido");

        if (this.monto.add(monto).compareTo(new BigDecimal("99999")) >= 0)
            throw new ArithmeticException("No se pudo transaccionar, se supera el monto máximo");

        this.monto = this.monto.add(monto);
        this.movimientos.add("+ $" + monto + " - " + motivo);
    }

    public void substractMonto(BigDecimal monto, String motivo) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0)
            throw new ArithmeticException("Monto invalido");

        if (this.monto.compareTo(monto) <= 0)
            throw new ArithmeticException("No se pudo transaccionar, monto insuficiente");

        this.monto = this.monto.subtract(monto);
        this.movimientos.add("- $" + monto + " - " + motivo);
    }

    public void setMovimientos(ArrayList<String> movimientos) {
        this.movimientos = movimientos;
    }

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getId() {
        return Id;
    }
}
