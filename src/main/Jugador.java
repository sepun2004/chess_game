public class Jugador {
    private String nombre;
    private boolean esBlanco;
    private int piezasCapturadas;

    public Jugador(String nombre, boolean esBlanco) {
        this.nombre = nombre;
        this.esBlanco = esBlanco;
        this.piezasCapturadas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isBlanco() {
        return esBlanco;
    }

    public int getPiezasCapturadas() {
        return piezasCapturadas;
    }

    public void capturarPieza() {
        piezasCapturadas++;
    }

    public void mostrarInfo() {
        String color = esBlanco ? "Blancas" : "Negras";
        System.out.println("Jugador: " + nombre + " | Color: " + color + " | Piezas capturadas: " + piezasCapturadas);
    }
}
