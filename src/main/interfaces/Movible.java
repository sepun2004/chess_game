package interfaces;

/**
 * Interfaz que define la capacidad de movimiento dentro del juego.
 */
public interface Movible {
    /**
     * Realiza el movimiento de una entidad a una nueva posición.
     * @param nuevaFila Destino en el eje X.
     * @param nuevaColumna Destino en el eje Y.
     * @return true si el movimiento se realizó con éxito.
     */
    boolean mover(int nuevaFila, int nuevaColumna);
}
