package piezas;

/**
 * Representa el Alfil. Se mueve únicamente en diagonal.
 */
public class Alfil extends Pieza {

    public Alfil(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " A " : " a ");
    }

    @Override
    public boolean esMovimientoValido(int destF, int destC, Pieza[][] tablero) {
        // En diagonal, el valor absoluto de la resta de filas debe ser igual al de columnas
        if (Math.abs(destF - this.fila) != Math.abs(destC - this.columna)) {
            return false;
        }

        // Verificar si el camino diagonal está despejado
        if (!esCaminoDiagonalDespejado(destF, destC, tablero)) {
            return false;
        }

        Pieza destino = tablero[destF][destC];
        return destino == null || destino.isEsBlanca() != this.esBlanca;
    }
}
