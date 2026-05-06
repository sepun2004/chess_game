package piezas;

/**
 * Representa la Torre. Se mueve en línea recta (filas o columnas).
 */
public class Torre extends Pieza {

    public Torre(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " T " : " t ");
    }

    @Override
    public boolean esMovimientoValido(int destF, int destC, Pieza[][] tablero) {
        // La torre se mueve si la fila es igual o la columna es igual
        if (this.fila != destF && this.columna != destC) {
            return false;
        }

        // Verificar si el camino está despejado (reutilizando lógica base)
        if (!esCaminoRectoDespejado(destF, destC, tablero)) {
            return false;
        }

        // Validar que el destino sea vacío o pieza enemiga
        Pieza destino = tablero[destF][destC];
        return destino == null || destino.isEsBlanca() != this.esBlanca;
    }
}
