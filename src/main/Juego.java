package main;

import java.util.Scanner;
import piezas.Pieza;
import piezas.Peon;

/**
 * Controlador principal del flujo del juego.
 */
public class Juego {
    private final Tablero tablero;
    private final Jugador j1;
    private final Jugador j2;
    private Jugador actual;

    public Juego() {
        this.tablero = new Tablero();
        this.j1 = new Jugador("Blanco", true);
        this.j2 = new Jugador("Negro", false);
        this.actual = j1;
    }

    public void jugar() {
        Scanner lector = new Scanner(System.in);
        boolean partidaActiva = true;

        System.out.println("=== AJEDREZ PROFESIONAL JAVA ===");

        while (partidaActiva) {
            tablero.mostrarTablero();
            System.out.println("\nTurno de: " + actual.getNombre() + " (" + (actual.isBlanco() ? "B" : "N") + ")");

            // Verificar jaque
            if (tablero.estaEnJaque(actual.isBlanco())) {
                System.out.println("⚠️  ¡JAQUE! Tu rey está bajo amenaza.");
                if (tablero.esJaqueMate(actual.isBlanco())) {
                    Jugador ganador = actual == j1 ? j2 : j1;
                    System.out.println("\n¡JAQUE MATE! El jugador " + ganador.getNombre() + " ha ganado.");
                    partidaActiva = false;
                    break;
                }
            }

            try {
                System.out.print("Fila Origen: ");
                int fO = lector.nextInt();
                System.out.print("Columna Origen: ");
                int cO = lector.nextInt();
                System.out.print("Fila Destino: ");
                int fD = lector.nextInt();
                System.out.print("Columna Destino: ");
                int cD = lector.nextInt();

                // Intentar realizar el movimiento
                Pieza resultado = tablero.moverPieza(fO, cO, fD, cD, actual.isBlanco());

                if (resultado != null) {
                    System.out.println(">> Movimiento confirmado.");

                    // Si se capturó una pieza real
                    if (resultado.getFila() != -1) {
                        actual.capturarPieza();
                    }

                    // Verificar promoción de peón
                    Pieza piezaMovida = tablero.obtenerPieza(fD, cD);
                    if (piezaMovida instanceof Peon && ((Peon) piezaMovida).debeSerPromovido()) {
                        System.out.println("\n¡Promoción de peón! Elige el tipo:");
                        System.out.println("R - Reina (recomendado)");
                        System.out.println("T - Torre");
                        System.out.println("A - Alfil");
                        System.out.println("C - Caballo");
                        System.out.print("Opción: ");
                        char opcion = lector.next().toUpperCase().charAt(0);
                        
                        if (tablero.promoverPeon(fD, cD, opcion)) {
                            System.out.println(">> Peón promovido a " + opcion);
                        } else {
                            System.out.println("!! Opción inválida. Peón promovido a Reina.");
                            tablero.promoverPeon(fD, cD, 'R');
                        }
                    }

                    // Cambiar turno
                    actual = (actual == j1) ? j2 : j1;

                } else {
                    System.out.println("!! Error: Movimiento ilegal o casilla vacía.");
                }

            } catch (Exception e) {
                System.out.println("!! Error: Entrada no válida. Use números del 0 al 7.");
                lector.nextLine();
            }
        }

        System.out.println("\n--- Resumen de la Partida ---");
        j1.mostrarInfo();
        j2.mostrarInfo();
        lector.close();
    }

    public static void main(String[] args) {
        new Juego().jugar();
    }
}