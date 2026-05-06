package piezas;

import interfaces.Movible;

/**
 * Clase abstracta que representa una pieza genérica de ajedrez.
 * Implementa la interfaz Movible y sirve de base para todas las piezas.
 */
public abstract class Pieza implements Movible {
    protected int fila;
    protected int columna;
    protected boolean esBlanca;
    protected String simbolo;

    /**
     * Constructor para una pieza.
     * @param fila Fila inicial.
     * @param columna Columna inicial.
     * @param esBlanca Color de la pieza (true para blancas, false para negras).
     * @param simbolo Representación textual de la pieza.
     */
    public Pieza(int fila, int columna, boolean esBlanca, String simbolo) {
        this.fila = fila;
        this.columna = columna;
        this.esBlanca = esBlanca;
        this.simbolo = simbolo;
    }

    // Getters y Setters (Encapsulación)
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
    public boolean isEsBlanca() { return esBlanca; }
    public String getSimbolo() { return simbolo; }

    /**
     * Método abstracto que cada pieza debe implementar según sus reglas.
     * @param destinoFila Fila de destino.
     * @param destinoColumna Columna de destino.
     * @param tablero Estado actual del tablero para validaciones.
     * @return true si el movimiento cumple las reglas de la pieza.
     */
    public abstract boolean esMovimientoValido(int destinoFila, int destinoColumna, Pieza[][] tablero);

    // Dentro de piezas/Pieza.java

    protected boolean esCaminoRectoDespejado(int destF, int destC, Pieza[][] tablero) {
        int pasoFila = Integer.compare(destF, this.fila); // 1, -1 o 0
        int pasoColumna = Integer.compare(destC, this.columna);

        int f = this.fila + pasoFila;
        int c = this.columna + pasoColumna;

        while (f != destF || c != destC) {
            if (tablero[f][c] != null) return false; // Hay algo en medio
            f += pasoFila;
            c += pasoColumna;
        }
        return true;
    }

    protected boolean esCaminoDiagonalDespejado(int destF, int destC, Pieza[][] tablero) {
        int pasoFila = (destF > this.fila) ? 1 : -1;
        int pasoColumna = (destC > this.columna) ? 1 : -1;

        int f = this.fila + pasoFila;
        int c = this.columna + pasoColumna;

        while (f != destF) {
            if (tablero[f][c] != null) return false;
            f += pasoFila;
            c += pasoColumna;
        }
        return true;
    }

    @Override
    public boolean mover(int nuevaFila, int nuevaColumna) {
        this.fila = nuevaFila;
        this.columna = nuevaColumna;
        return true;
    }
}
