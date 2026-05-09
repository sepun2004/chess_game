# Chess Game

Un juego de ajedrez implementado en Java que permite jugar entre dos jugadores desde consola.

## Compilación

Para compilar el proyecto, ejecuta:

```bash
javac -d out src/interfaces/*.java src/piezas/*.java src/main/*.java
```

(Los archivos compilados se guardarán en la carpeta `out/`)

## Ejecución

Para ejecutar el juego:

```bash
java -cp out main.Juego
```

## Cómo jugar

1. El juego solicita las coordenadas de origen (fila y columna de la pieza a mover)
2. Luego solicita las coordenadas de destino
3. Los turnos se alternan entre jugadores (Blanco y Negro)
4. El juego detecta automáticamente **jaque** y **jaque mate**
5. Cuando un peón llega a la última fila, se promueve automáticamente a otra pieza
6. El enroque se realiza como un movimiento normal del rey (2 casillas hacia la torre)

Las coordenadas van del 0 al 7 (filas y columnas).

## Estructura del proyecto

- **src/main/** - Clases principales del juego (Juego, Tablero, Jugador)
- **src/piezas/** - Clases de las piezas y clase abstracta Pieza
- **src/interfaces/** - Interfaz Movible

## Decisiones de diseño

- Utilizamos una clase abstracta `Pieza` que define el comportamiento común de todas las piezas
- Cada pieza implementa su propia lógica de validación de movimientos en `esMovimientoValido()`
- La interfaz `Movible` define el contrato para el movimiento de piezas
- Los atributos son privados/protegidos con getters y setters para encapsulación
- **Polimorfismo:** En lugar de usar `instanceof`, utilizamos métodos polimórficos como `esRey()`, `esTorre()`, `esPeon()` para identificar tipos de piezas de manera limpia y profesional
- **Método genérico de marcado:** `marcarMovimiento()` se implementa en cada subclase que lo necesita, simplificando la lógica del tablero
- **Jaque y Jaque Mate:** Métodos en `Tablero` que validan el estado del rey en cada turno
- **Enroque:** Validado mediante `puedeHacerEnroque()` que verifica que rey y torre no hayan movido
- **Promoción:** El peón detecta automáticamente cuando alcanza la última fila y se promueve
- **Tracking de movimientos:** Clases `Rey` y `Torre` contienen flags `haMovido` para rastrear si han movido (accesibles vía `esHaMovido()`)

## Características implementadas

### Funcionalidad básica
- Movimientos válidos para todas las piezas (Rey, Reina, Torre, Alfil, Caballo, Peón)
- Validación de capturas
- Turnos alternos
- Sistema de puntuación (piezas capturadas)

### Funcionalidades avanzadas
- **Jaque** - Detección cuando el rey está bajo amenaza
- **Jaque Mate** - Fin de partida cuando no hay escapes posibles
- **Enroque** - Movimiento especial reino-torre (solo si no han movido antes y no hay jaque)
- **Promoción de Peón** - Conversión automática a Reina/Torre/Alfil/Caballo en la última fila