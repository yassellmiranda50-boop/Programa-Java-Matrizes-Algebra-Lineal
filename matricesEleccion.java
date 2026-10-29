import java.util.Scanner;

public class matricesEleccion {

    // Version base hecha por Yassell:
    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int eleccion;
        double[][] matrizPrincipal = null;

        do {
            eleccion = seleccion();
            switch (eleccion) {
                case 1 -> {
                    matrizPrincipal = ingresarMatriz();
                    //factorizacionLU(matrizPrincipal);
                    ejecutarFactorizacionLU(matrizPrincipal);
                }
                case 2 -> {
                    matrizPrincipal = ingresarMatriz();
                    //transformacionesElementales(matrizPrincipal); Elijan el nombre que deseen, siempre con sentido si
                }
                case 3 -> {
                    System.out.println("Saliendo del programa...");
                }
                default -> {
                    System.out.println("Error en ingreso del dato solicitado, intentelo nuevamente\n");
                }
            }
        } while (eleccion != 3);
    }

    static int seleccion() {
        System.out.println("""
            Bienvenido al programa sobre nuestro último trabajo en algebra lineal.

            Ingrese el número de la opción que desea realizar:
            1. Factorizacion LxU.
            2. Transformaciones elementales.
            3. Salir del programa.
            """);
        
            try {
        return Integer.parseInt(leer.nextLine().trim());
    } catch (NumberFormatException e) {
        return -1; // valor inválido: cae en el default del switch
    }
    }

    static double[][] ingresarMatriz() {
        double[][] matrizIngresada = new double[3][3];

        for (int i = 0; i < matrizIngresada.length; i++) {
            for (int j = 0; j < matrizIngresada[i].length; j++) {
                System.out.println("Ingrese el valor en la posición: " + (i + 1) + ", " + (j + 1));
                try {
                    matrizIngresada[i][j] = Double.parseDouble(leer.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error en ingreso del dato solicitado, intentelo nuevamente\n");
                    j--; // Reintentar la misma posición
                }
            }
        }

        return matrizIngresada;
    }
    // Final de la base.

    //==============================================================

    // ======================================
    // FACTORIZACIÓN LU (método de Doolittle) - Alvaro Vargas
    // ======================================

    // Convierte un número a fracción si es posible (ej: 1.5 -> 3/2)
    static String formato(double v) {
        for (int d = 1; d <= 100; d++) {
            long n = Math.round(v * d);
            if (Math.abs(v * d - n) < 0.0001) return d == 1 ? "" + n : n + "/" + d;
        }
        return String.format("%.4f", v);
    }

    // Imprime una matriz con formato
    static void imprimir(String nombre, double[][] M) {
        System.out.println("\n" + nombre + ":");
        for (double[] fila : M) {
            System.out.print("  [");
            for (double v : fila) System.out.printf(" %8s", formato(v));
            System.out.println(" ]");
        }
    }

 // Ejecuta la factorización LU
    static void ejecutarFactorizacionLU(double[][] A) {
        if (A == null) {
            System.out.println("No hay una matriz ingresada para factorizar.");
            return;
        }

        double[][][] resultado = factorizarLU(A);

        if (resultado == null) {
            System.out.println("\nNo se puede realizar la factorización LU.");
            System.out.println("Algún pivote es cero.");
        } else {
            double[][] L = resultado[0];
            double[][] U = resultado[1];
            double[][] C = resultado[2]; // C = L x U

            System.out.println("\n--- RESULTADO DE LA FACTORIZACIÓN LU ---");
            imprimir("MATRIZ A (entrada)", A);
            imprimir("MATRIZ L", L);
            imprimir("MATRIZ U", U);
            imprimir("MATRIZ RESULTANTE (L x U)", C);

            System.out.println("\n¿L x U es igual a A? " + matricesIguales(A, C));
        }
    }

    /**
     * Realiza la factorización LU de una matriz 3x3 (método de Doolittle).
     * matriz de entrada 3x3 ya ingresada (no se modifica)
     * un arreglo double[][][] con {L, U, C} donde C = L x U,
     * o null si no se puede factorizar porque algún pivote es cero
     */
    static double[][][] factorizarLU(double[][] A) {
        int n = 3;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];
        double[][] C = new double[n][n];

        // Doolittle: en cada paso i se calcula la fila i de U y la columna i de L
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;

            for (int j = i; j < n; j++) {
                U[i][j] = A[i][j];
                for (int k = 0; k < i; k++) U[i][j] -= L[i][k] * U[k][j];
            }

            if (i < n - 1 && U[i][i] == 0) {
                return null; // No se puede realizar la factorización LU
            }

            for (int j = i + 1; j < n; j++) {
                L[j][i] = A[j][i];
                for (int k = 0; k < i; k++) L[j][i] -= L[j][k] * U[k][i];
                L[j][i] /= U[i][i];
            }
        }

        // C = L x U (debe dar igual que A)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += L[i][k] * U[k][j];
                }
            }
        }

        return new double[][][]{L, U, C};
    }

    // Compara si dos matrices son iguales (con tolerancia para errores de punto flotante)
    static boolean matricesIguales(double[][] A, double[][] B) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (Math.abs(A[i][j] - B[i][j]) > 1e-9) return false;
            }
        }
        return true;
    }
    //===================================================
}
