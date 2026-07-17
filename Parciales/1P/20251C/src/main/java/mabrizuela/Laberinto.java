package mabrizuela;

public class Laberinto {

    public static boolean existeCamino(int[][] laberinto, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        ///  LIMITES
        if (filaInicio < 0 || filaInicio >= laberinto.length ||
                columnaInicio < 0 || columnaInicio >= laberinto[0].length) {
            return false;
        }

        /// PARED O VISITADO
        if (laberinto[filaInicio][columnaInicio] == 1 || laberinto[filaInicio][columnaInicio] == -1) {
            return false;
        }

        ///  FIN
        if (filaInicio == filaFin && columnaInicio == columnaFin) {
            return true;
        }
        
        laberinto[filaInicio][columnaInicio] = -1;

        // probar las 4 direcciones
        return existeCamino(laberinto, filaInicio - 1, columnaInicio, filaFin, columnaFin) ||       ///  UP
                existeCamino(laberinto, filaInicio + 1, columnaInicio, filaFin, columnaFin) ||      /// DOWN
                existeCamino(laberinto, filaInicio, columnaInicio - 1, filaFin, columnaFin) ||  /// LEFT
                existeCamino(laberinto, filaInicio, columnaInicio + 1, filaFin, columnaFin);    /// RIGHT
    }

    public static void main(String[] args) {
        int[][] laberinto = {
                {0, 0, 1, 0},
                {1, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };

        boolean existe = existeCamino(laberinto, 0, 0, 3, 0);
        if (existe) {
            System.out.println("Existe un camino en el laberinto.");
        } else {
            System.out.println("No existe un camino en el laberinto.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberinto);

        int[][] laberintoSinSalida = {
                {0, 0, 1, 0},
                {1, 0, 1, 1},
                {0, 0, 0, 0},
                {0, 1, 1, 1}
        };
        boolean existeSinSalida = existeCamino(laberintoSinSalida, 0, 0, 0, 3);
        if (existeSinSalida) {
            System.out.println("Existe un camino en el laberinto sin salida (¡error!).");
        } else {
            System.out.println("No existe un camino en el laberinto sin salida.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberintoSinSalida);
    }

    public static void imprimirLaberinto(int[][] laberinto) {
        for (int[] fila : laberinto) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}
