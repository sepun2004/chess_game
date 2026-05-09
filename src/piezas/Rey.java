package piezas;

/**
 * Clase que representa al Rey.
 * Movimiento limitado a una casilla en cualquier dirección.
 */
public class Rey extends Pieza {
    private boolean haMovido;

    /**
     * Constructor del Rey.
     * @param fila Fila inicial.
     * @param columna Columna inicial.
     * @param esBlanca Color de la pieza.
     */
    public Rey(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " R " : " r ");
        this.haMovido = false;
    }

    /**
     * Marca que el rey ha realizado su primer movimiento.
     */
    public void marcarComoMovido() {
        this.haMovido = true;
    }

    /**
     * Verifica si la torre ha movido antes.
     * @return true si la torre ya se ha movido.
     */
    public boolean esHaMovido() {
        return haMovido;
    }

    @Override
    public void marcarMovimiento() {
        marcarComoMovido();
    }

    @Override
    public boolean esRey() {
        return true;
    }

    @Override
    public boolean esMovimientoValido(int destF, int destC, Pieza[][] tablero) {
        // Calculamos cuántas casillas se mueve en cada eje
        int difFila = Math.abs(destF - this.fila);
        int difColumna = Math.abs(destC - this.columna);

        // El rey solo se mueve 1 casilla (distancia máxima en cualquier eje es 1)
        if (difFila <= 1 && difColumna <= 1) {
            // No se puede quedar en el mismo sitio
            if (difFila == 0 && difColumna == 0) {
                return false;
            }

            Pieza objetivo = tablero[destF][destC];
            // La casilla debe estar vacía o tener un enemigo
            if (objetivo == null || objetivo.isEsBlanca() != this.esBlanca) {
                return true;
            }
        }
        return false;
    }
}