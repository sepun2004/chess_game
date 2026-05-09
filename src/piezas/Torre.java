package piezas;

/**
 * Representa la Torre. Se mueve en línea recta (filas o columnas).
 */
public class Torre extends Pieza {
    private boolean haMovido;

    /**
     * Constructor de la Torre.
     * @param fila Fila inicial.
     * @param columna Columna inicial.
     * @param esBlanca Color de la pieza.
     */
    public Torre(int fila, int columna, boolean esBlanca) {
        super(fila, columna, esBlanca, esBlanca ? " T " : " t ");
        this.haMovido = false;
    }

    /**
     * Marca que la torre ha realizado su primer movimiento.
     */
    public void marcarComoMovida() {
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
        marcarComoMovida();
    }

    @Override
    public boolean esTorre() {
        return true;
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
