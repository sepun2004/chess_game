package piezas;

/**
 * Clase que representa al Peón.
 */
public class Peon extends Pieza {

    /**
     * Constructor del Peón.
     * @param fila Fila inicial.
     * @param columna Columna inicial.
     * @param esBlanca Color de la pieza.
     */
    public Peon(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " P " : " p ");
    }

    @Override
    public boolean esMovimientoValido(int destF, int destC, Pieza[][] tablero) {
        // Determinamos la dirección: las blancas restan fila, las negras suman
        int direccion = this.esBlanca ? -1 : 1;

        // 1. Movimiento hacia adelante (una casilla)
        if (destC == this.columna) {
            if (destF == this.fila + direccion) {
                // Solo si la casilla de destino está vacía
                if (tablero[destF][destC] == null) {
                    return true;
                }
            }

            // 2. Movimiento inicial doble (dos casillas)
            int filaSalida = this.esBlanca ? 6 : 1;
            if (this.fila == filaSalida && destF == this.fila + (2 * direccion)) {
                // El camino y el destino deben estar vacíos
                if (tablero[this.fila + direccion][this.columna] == null && tablero[destF][destC] == null) {
                    return true;
                }
            }
        }

        // 3. Captura diagonal
        if (destF == this.fila + direccion && (destC == this.columna + 1 || destC == this.columna - 1)) {
            Pieza piezaObjetivo = tablero[destF][destC];
            // Debe haber una pieza enemiga
            if (piezaObjetivo != null && piezaObjetivo.isEsBlanca() != this.esBlanca) {
                return true;
            }
        }

        return false;
    }
}
