package piezas;

/**
 * Representa la Reina. Combina los movimientos de la Torre y el Alfil.
 */
public class Reina extends Pieza {

    public Reina(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " Q " : " q ");
    }

    @Override
    public boolean esMovimientoValido(int destF, int destC, Pieza[][] tablero) {
        boolean comoTorre = (this.fila == destF || this.columna == destC);
        boolean comoAlfil = (Math.abs(destF - this.fila) == Math.abs(destC - this.columna));

        if (comoTorre) {
            if (esCaminoRectoDespejado(destF, destC, tablero)) {
                Pieza destino = tablero[destF][destC];
                return destino == null || destino.isEsBlanca() != this.esBlanca;
            }
        } else if (comoAlfil) {
            if (esCaminoDiagonalDespejado(destF, destC, tablero)) {
                Pieza destino = tablero[destF][destC];
                return destino == null || destino.isEsBlanca() != this.esBlanca;
            }
        }

        return false;
    }
}
