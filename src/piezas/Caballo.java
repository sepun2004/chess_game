package piezas;

/**
 * Representa el Caballo. Se mueve en "L" y puede saltar piezas.
 */
public class Caballo extends Pieza {

    public Caballo(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " C " : " c ");
    }

    @Override
    public boolean esMovimientoValido(int destF, int destC, Pieza[][] tablero) {
        int difFila = Math.abs(destF - this.fila);
        int difColumna = Math.abs(destC - this.columna);

        // Movimiento en L: (2 y 1) o (1 y 2)
        boolean esMovimientoL = (difFila == 2 && difColumna == 1) || (difFila == 1 && difColumna == 2);

        if (esMovimientoL) {
            Pieza destino = tablero[destF][destC];
            // No necesita verificar el camino, solo si el destino es válido
            return destino == null || destino.isEsBlanca() != this.esBlanca;
        }

        return false;
    }
}
