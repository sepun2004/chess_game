import java.util.Scanner;


public class Juego {
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;

    public Juego(String nombre1, String nombre2) {
        this.tablero = new Tablero();
        this.jugador1 = new Jugador(nombre1, true);
        this.jugador2 = new Jugador(nombre2, false);
        this.jugadorActual = jugador1;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

    public void iniciarPartida() {
        System.out.println("=== Bienvenido al Juego de Ajedrez ===");
        System.out.println("Jugador 1: " + jugador1.getNombre() + " (Blancas)");
        System.out.println("Jugador 2: " + jugador2.getNombre() + " (Negras)");
        System.out.println("======================================\n");
        
        jugar();
    }

    private void jugar() {
        boolean enPartida = true;
        
        while (enPartida) {
            tablero.mostrarTablero();
            System.out.println("\nTurno de: " + jugadorActual.getNombre());
            System.out.print("Ingrese fila de origen (0-7) o 'salir' para terminar: ");
            
            String entrada = System.console() != null ? System.console().readLine() : "salir";
            
            if (entrada.equalsIgnoreCase("salir")) {
                enPartida = false;
                System.out.println("\n¡Partida terminada!");
                mostrarResultados();
            } else {
                cambiarTurno();
            }
        }
    }

    private void mostrarResultados() {
        System.out.println("\n=== Resultados Finales ===");
        jugador1.mostrarInfo();
        jugador2.mostrarInfo();
    }

    public static void main(String[] args) {
        Juego juego = new Juego("Jugador 1", "Jugador 2");
        juego.iniciarPartida();
    }
}
