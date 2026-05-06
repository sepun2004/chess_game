package piezas;

/**
 * Clase que representa al Rey.
 * Movimiento limitado a una casilla en cualquier dirección.
 */
public class Rey extends Pieza {

    public Rey(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " R " : " r ");
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