package main;

/**
 * Entidad que representa a un participante en el torneo de ajedrez 1vs1.
 * Gestiona la identidad del usuario, su color asignado y las estadísticas de captura.
 */
public class Jugador {
    // Atributos finales para garantizar inmutabilidad tras la creación
    private final String nombre;
    private final boolean esBlanco;
    private int piezasCapturadas;

    /**
     * Constructor único para inicializar un jugador.
     * @param nombre Nombre identificativo del jugador.
     * @param esBlanco Verdadero si el jugador controla las piezas blancas.
     */
    public Jugador(String nombre, boolean esBlanco) {
        this.nombre = nombre;
        this.esBlanco = esBlanco;
        this.piezasCapturadas = 0;
    }

    // --- Getters (Convención Java Beans) ---

    public String getNombre() {
        return nombre;
    }

    /**
     * Indica si el jugador posee el turno de las piezas blancas.
     * @return true si es blanco, false si es negro.
     */
    public boolean isBlanco() {
        return esBlanco;
    }

    public int getPiezasCapturadas() {
        return piezasCapturadas;
    }

    // --- Métodos de Lógica de Negocio ---

    /**
     * Incrementa el contador de piezas capturadas por el jugador.
     * Este método es invocado por la clase Juego tras un movimiento exitoso a casilla ocupada.
     */
    public void capturarPieza() {
        this.piezasCapturadas++;
    }

    /**
     * Imprime en consola el estado actual del jugador de forma formateada.
     */
    public void mostrarInfo() {
        String colorDesc = esBlanco ? "Blancas" : "Negras";
        System.out.printf("Jugador: %-10s | Color: %-7s | Capturas: %d%n",
                nombre, colorDesc, piezasCapturadas);
    }
}