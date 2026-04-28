public class Tablero {
    private static final int TAMAÑO = 8;
    private String[][] tablero;

    public Tablero() {
        tablero = new String[TAMAÑO][TAMAÑO];
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                tablero[i][j] = " . ";
            }
        }
    }

    public String obtenerCasilla(int fila, int columna) {
        if (fila >= 0 && fila < TAMAÑO && columna >= 0 && columna < TAMAÑO) {
            return tablero[fila][columna];
        }
        return null;
    }

    public void establecerCasilla(int fila, int columna, String pieza) {
        if (fila >= 0 && fila < TAMAÑO && columna >= 0 && columna < TAMAÑO) {
            tablero[fila][columna] = pieza;
        }
    }

    public void mostrarTablero() {
        System.out.println("\n  0   1   2   3   4   5   6   7");
        for (int i = 0; i < TAMAÑO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMAÑO; j++) {
                System.out.print(tablero[i][j]);
            }
            System.out.println();
        }
    }

    public boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < TAMAÑO && columna >= 0 && columna < TAMAÑO;
    }
}
