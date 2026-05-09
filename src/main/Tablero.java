package main;

import piezas.*;

/**
 * Gestiona el estado del tablero de ajedrez de 8x8.
 * Implementa la lógica de disposición inicial y validación de movimientos.
 */
public class Tablero {
    private static final int TAMANO = 8;
    private final Pieza[][] casillas;

    public Tablero() {
        this.casillas = new Pieza[TAMANO][TAMANO];
        inicializarTablero();
    }

    private void inicializarTablero() {
        // Piezas Negras
        colocarPiezasMayores(0, false);
        for (int j = 0; j < TAMANO; j++) {
            casillas[1][j] = new Peon(1, j, false);
        }

        // Piezas Blancas
        colocarPiezasMayores(7, true);
        for (int j = 0; j < TAMANO; j++) {
            casillas[6][j] = new Peon(6, j, true);
        }
    }

    private void colocarPiezasMayores(int fila, boolean esBlanca) {
        casillas[fila][0] = new Torre(fila, 0, esBlanca);
        casillas[fila][1] = new Caballo(fila, 1, esBlanca);
        casillas[fila][2] = new Alfil(fila, 2, esBlanca);
        casillas[fila][3] = new Reina(fila, 3, esBlanca);
        casillas[fila][4] = new Rey(fila, 4, esBlanca);
        casillas[fila][5] = new Alfil(fila, 5, esBlanca);
        casillas[fila][6] = new Caballo(fila, 6, esBlanca);
        casillas[fila][7] = new Torre(fila, 7, esBlanca);
    }

    /**
     * Mueve una pieza y gestiona la captura, enroque y promoción.
     * @return La pieza capturada si la hubo, null si no hubo captura o el movimiento falló.
     */
    public Pieza moverPieza(int fO, int cO, int fD, int cD, boolean esBlanco) {
        if (!esPosicionValida(fO, cO) || !esPosicionValida(fD, cD)) return null;

        Pieza p = casillas[fO][cO];
        if (p == null || p.isEsBlanca() != esBlanco) return null;

        // Verificar si es un intento de enroque
        if (p instanceof Rey && Math.abs(cD - cO) == 2) {
            if (puedeHacerEnroque(fO, cO, cD, esBlanco)) {
                hacerEnroque(fO, cO, cD);
                return new Peon(-1, -1, !esBlanco); // Retornar un dummy
            }
            return null;
        }

        if (p.esMovimientoValido(fD, cD, casillas)) {
            Pieza capturada = casillas[fD][cD];

            // Ejecutar el movimiento
            casillas[fD][cD] = p;
            casillas[fO][cO] = null;
            p.mover(fD, cD);

            // Marcar movimiento del Rey y Torre para enroque
            if (p instanceof Rey) {
                ((Rey) p).marcarComoMovido();
            } else if (p instanceof Torre) {
                ((Torre) p).marcarComoMovida();
            }

            // Retornar la pieza capturada o dummy
            return (capturada == null) ? new Peon(-1, -1, !esBlanco) : capturada;
        }
        return null;
    }

    /**
     * Verifica y ejecuta la promoción de un peón.
     * @param fila Fila del peón.
     * @param columna Columna del peón.
     * @param tipoPieza Tipo de pieza destino ('R' para Reina, 'T' para Torre, 'A' para Alfil, 'C' para Caballo).
     * @return true si la promoción se realizó correctamente.
     */
    public boolean promoverPeon(int fila, int columna, char tipoPieza) {
        Pieza p = casillas[fila][columna];
        if (!(p instanceof Peon) || !((Peon) p).debeSerPromovido()) return false;

        boolean esBlanca = p.isEsBlanca();
        Pieza nuevaPieza = null;

        switch (tipoPieza) {
            case 'R':
                nuevaPieza = new Reina(fila, columna, esBlanca);
                break;
            case 'T':
                nuevaPieza = new Torre(fila, columna, esBlanca);
                break;
            case 'A':
                nuevaPieza = new Alfil(fila, columna, esBlanca);
                break;
            case 'C':
                nuevaPieza = new Caballo(fila, columna, esBlanca);
                break;
            default:
                return false;
        }

        casillas[fila][columna] = nuevaPieza;
        return true;
    }

    public boolean esPosicionValida(int f, int c) {
        return f >= 0 && f < TAMANO && c >= 0 && c < TAMANO;
    }

    /**
     * Obtiene la pieza en una posición específica.
     * @param fila Fila.
     * @param columna Columna.
     * @return La pieza en esa posición, o null si está vacía.
     */
    public Pieza obtenerPieza(int fila, int columna) {
        if (esPosicionValida(fila, columna)) {
            return casillas[fila][columna];
        }
        return null;
    }

    public void mostrarTablero() {
        System.out.println("\n    0  1  2  3  4  5  6  7");
        System.out.println("  -------------------------");
        for (int i = 0; i < TAMANO; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < TAMANO; j++) {
                System.out.print(casillas[i][j] == null ? " . " : casillas[i][j].getSimbolo());
            }
            System.out.println("|");
        }
        System.out.println("  -------------------------");
    }

    /**
     * Verifica si una posición está bajo ataque por el equipo contrario.
     * @param fila Fila a verificar.
     * @param columna Columna a verificar.
     * @param esBlanca true si verificamos ataques por piezas negras, false para blancas.
     * @return true si la posición está bajo ataque.
     */
    public boolean estaBajoAtaque(int fila, int columna, boolean esBlanca) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                Pieza p = casillas[i][j];
                // Si hay una pieza enemiga
                if (p != null && p.isEsBlanca() != esBlanca && !p.getClass().getSimpleName().equals("Rey")) {
                    // Verificar si puede atacar la posición
                    if (p.esMovimientoValido(fila, columna, casillas)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica si el rey está en jaque.
     * @param esBlanca true para verificar el rey blanco, false para el rey negro.
     * @return true si el rey está en jaque.
     */
    public boolean estaEnJaque(boolean esBlanca) {
        Rey rey = encontrarRey(esBlanca);
        if (rey == null) return false;
        return estaBajoAtaque(rey.getFila(), rey.getColumna(), esBlanca);
    }

    /**
     * Encuentra el Rey en el tablero.
     * @param esBlanca true para encontrar el rey blanco, false para el negro.
     * @return El rey, o null si no existe.
     */
    private Rey encontrarRey(boolean esBlanca) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (casillas[i][j] instanceof Rey && casillas[i][j].isEsBlanca() == esBlanca) {
                    return (Rey) casillas[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Verifica si es jaque mate.
     * @param esBlanca true para verificar el rey blanco, false para el negro.
     * @return true si es jaque mate.
     */
    public boolean esJaqueMate(boolean esBlanca) {
        if (!estaEnJaque(esBlanca)) return false;

        // Verificar si hay algún movimiento legal posible
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                Pieza p = casillas[i][j];
                if (p != null && p.isEsBlanca() == esBlanca) {
                    // Probar todos los movimientos posibles
                    for (int fD = 0; fD < TAMANO; fD++) {
                        for (int cD = 0; cD < TAMANO; cD++) {
                            if (p.esMovimientoValido(fD, cD, casillas)) {
                                // Simular el movimiento
                                Pieza respaldo = casillas[fD][cD];
                                casillas[fD][cD] = p;
                                casillas[i][j] = null;
                                int filaAnterior = p.getFila();
                                int colAnterior = p.getColumna();
                                p.setFila(fD);
                                p.setColumna(cD);

                                // Verificar si el rey sigue en jaque
                                boolean enJaque = estaEnJaque(esBlanca);

                                // Deshacer movimiento
                                casillas[i][j] = p;
                                casillas[fD][cD] = respaldo;
                                p.setFila(filaAnterior);
                                p.setColumna(colAnterior);

                                if (!enJaque) return false; // Hay un movimiento legal
                            }
                        }
                    }
                }
            }
        }
        return true; // No hay movimientos legales
    }

    /**
     * Valida si se puede hacer un enroque.
     * @param fO Fila origen del rey.
     * @param cO Columna origen del rey.
     * @param cD Columna destino (para identificar si es lado de reina o rey).
     * @param esBlanca Color del rey.
     * @return true si el enroque es válido.
     */
    public boolean puedeHacerEnroque(int fO, int cO, int cD, boolean esBlanca) {
        // El enroque solo se hace desde la posición inicial del rey
        if (cO != 4) return false;
        if ((esBlanca && fO != 7) || (!esBlanca && fO != 0)) return false;

        Rey rey = encontrarRey(esBlanca);
        if (rey == null || rey.esHaMovido()) return false;

        Torre torre = null;
        int cTorre;

        // Determinar si es enroque corto (lado del rey) o largo (lado de la reina)
        if (cD > cO) {
            // Enroque corto (lado del rey)
            cTorre = 7;
        } else {
            // Enroque largo (lado de la reina)
            cTorre = 0;
        }

        // Buscar la torre
        Pieza p = casillas[fO][cTorre];
        if (!(p instanceof Torre) || ((Torre) p).esHaMovido()) return false;
        torre = (Torre) p;

        // Verificar que no hay piezas entre rey y torre
        int inicio = Math.min(cO, cTorre) + 1;
        int fin = Math.max(cO, cTorre);
        for (int c = inicio; c < fin; c++) {
            if (casillas[fO][c] != null) return false;
        }

        // Verificar que el rey no está en jaque, ni pasa por jaque
        if (estaEnJaque(esBlanca)) return false;
        if (estaBajoAtaque(fO, cD > cO ? 5 : 3, esBlanca)) return false;

        return true;
    }

    /**
     * Realiza un enroque.
     * @param fO Fila del rey.
     * @param cO Columna del rey.
     * @param cD Columna destino.
     */
    public void hacerEnroque(int fO, int cO, int cD) {
        Rey rey = (Rey) casillas[fO][cO];
        int cTorre = cD > cO ? 7 : 0;
        Torre torre = (Torre) casillas[fO][cTorre];

        // Mover el rey
        int cReyDestino = cD > cO ? 6 : 2;
        casillas[fO][cReyDestino] = rey;
        casillas[fO][cO] = null;
        rey.mover(fO, cReyDestino);
        rey.marcarComoMovido();

        // Mover la torre
        int cTorreDestino = cD > cO ? 5 : 3;
        casillas[fO][cTorreDestino] = torre;
        casillas[fO][cTorre] = null;
        torre.mover(fO, cTorreDestino);
        torre.marcarComoMovida();
    }
}
