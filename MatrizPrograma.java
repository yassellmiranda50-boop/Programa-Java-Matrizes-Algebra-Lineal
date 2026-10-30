
import java.util.Scanner;

public class MatrizPrograma {

    static Scanner leer = new Scanner(System.in); // Creamos el escaner para esta clase

    public static void main(String[] args) {
        int eleccion;
        double[][] matrizPrincipal;

        do {
            eleccion = seleccion();
            switch (eleccion) {
                case 1 -> {
                    matrizPrincipal = IngresoMatriz.ingresarMatriz(); // Le asignamos el valor retornado del metodo ingresarMatriz(); de la clase IngresoMatriz.
                    procesoFactorizacionLU.ejecutarFactorizacionLU(matrizPrincipal); // Llamada a la función de factorización LU
                }
                case 2 -> {
                    transformaciones_elementales.iniciar(leer); // LLamado del archivo de transformaciones elementales.
                }
                case 3 -> {
                    System.out.println("Saliendo del programa..."); // Mensaje indicando la finalización del programa.
                }
                default -> {
                    System.out.println("\nError en ingreso del dato solicitado, intentelo nuevamente"); // Si se escribe un valor no indicado, se pide nuevamente el ingreso
                }
            }
        } while (eleccion != 3);
    }

    static int seleccion() {
        System.out.println("""
            \n--------------------------------------------------
            Ingrese el número de la opción que desea realizar:
            1. Factorizacion LxU.
            2. Transformaciones elementales.
            3. Salir del programa. """);

        try {
            return Integer.parseInt(leer.nextLine().trim()); // Leemos la seleccion como cadena y la convertimos a entero.
        } catch (NumberFormatException e) {
            return -1; // Si la conversion falla lo atrapamos. Devolvemos -1 para que el default del switch muestre el mensaje.
        }
    }


    class IngresoMatriz {
        static Scanner sc = new Scanner(System.in);

        static double[][] ingresarMatriz() {
            double[][] matrizIngresada = new double[3][3];

            for (int i = 0; i < matrizIngresada.length; i++) {
                for (int j = 0; j < matrizIngresada[i].length; j++) {
                    System.out.println("Ingrese el valor en la posición: " + (i + 1) + ", " + (j + 1));
                    try {
                        matrizIngresada[i][j] = Double.parseDouble(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("\nError en ingreso del dato solicitado, intentelo nuevamente\n");
                        j--; // Reintentar la misma posición
                    }
                }
            }

            return matrizIngresada;
        }
    }

    class procesoFactorizacionLU {
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
                ImprimirFormatearMatrices.imprimir("MATRIZ A (entrada)", A);
                ImprimirFormatearMatrices.imprimir("MATRIZ L", L);
                ImprimirFormatearMatrices.imprimir("MATRIZ U", U);
                ImprimirFormatearMatrices.imprimir("MATRIZ RESULTANTE (L x U)", C);

                System.out.println("\n¿Matriz L x U es igual a matriz A? " + ImprimirFormatearMatrices.matricesIguales(A, C));
            }
        }

        /**
         * Realiza la factorización LU de una matriz 3x3 (método de Doolittle).
         * matriz de entrada 3x3 ya ingresada (no se modifica) un arreglo
         * double[][][] con {L, U, C} donde C = L x U, o null si no se puede
         * factorizar porque algún pivote es cero
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
                    for (int k = 0; k < i; k++) {
                        U[i][j] -= L[i][k] * U[k][j];
                    }
                }

                if (i < n - 1 && U[i][i] == 0) {
                    return null; // No se puede realizar la factorización LU
                }

                for (int j = i + 1; j < n; j++) {
                    L[j][i] = A[j][i];
                    for (int k = 0; k < i; k++) {
                        L[j][i] -= L[j][k] * U[k][i];
                    }
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
    }


    class ImprimirFormatearMatrices {
        // Convierte un número a fracción si es posible (ej: 1.5 -> 3/2)

        static String formato(double v) {
            for (int d = 1; d <= 100; d++) {
                long n = Math.round(v * d);
                if (Math.abs(v * d - n) < 0.0001) {
                    return d == 1 ? "" + n : n + "/" + d;
                }
            }
            return String.format("%.4f", v);
        }

        // Imprime una matriz con formato
        static void imprimir(String nombre, double[][] M) {
            System.out.println("\n" + nombre + ":");
            for (double[] fila : M) {
                System.out.print("  [");
                for (double v : fila) {
                    System.out.printf(" %8s", formato(v));
                }
                System.out.println(" ]");
            }
        }

        // Compara si dos matrices son iguales (con tolerancia para errores de punto flotante)
        static String matricesIguales(double[][] A, double[][] B) {
            boolean comparacionValida = true;
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[i].length; j++) {
                    if (Math.abs(A[i][j] - B[i][j]) > 1e-9) {
                        comparacionValida = false;
                    }
                }
            }

            String mensajeAfirmacion;

            if(!comparacionValida){
                mensajeAfirmacion = "\nR: No son iguales.";
            } else{
                mensajeAfirmacion = "\nR: Son iguales.";
            }

            return mensajeAfirmacion;
        }
    }
}
