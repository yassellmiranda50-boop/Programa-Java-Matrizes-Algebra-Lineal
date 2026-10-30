
import java.util.Scanner;

public class transformaciones_elementales {

    private static Scanner sc;
    private static final double EPSILON = 1e-10;

    public static void main(String[] args) {
        iniciar(new Scanner(System.in));
    }

    static void iniciar(Scanner scanner) {
        sc = scanner;
        int eleccion;

        do {
            eleccion = seleccion();
            switch (eleccion) {
                case 1 ->
                    procesar_matriz("Matriz escalonada superior", 1);
                case 2 ->
                    procesar_matriz("Matriz escalonada inferior", 2);
                case 3 ->
                    procesar_matriz("Matriz identidad", 3);
                case 4 ->
                    System.out.println("\nVOLVIENDO AL MENÚ PRINCIPAL...");
                default ->
                    System.out.println("Opción inválida. Inténtelo nuevamente.");
            }
        } while (eleccion != 4);
    }

    private static void procesar_matriz(String titulo, int tipo) {
        double[][] matriz = ingresarMatriz();
        double[][] resultado;

        switch (tipo) {
            case 1 ->
                resultado = matriz_superior(matriz);
            case 2 ->
                resultado = matriz_inferior(matriz);
            case 3 ->
                resultado = matriz_identidad(matriz);
            default ->
                throw new IllegalArgumentException("Tipo de transformación desconocido.");
        }

        if (resultado == null) {
            System.out.println("No se puede reducir esta matriz a la identidad porque es singular.");
            return;
        }

        System.out.println(titulo + ":");
        imprimirMatriz(resultado);
    }

    private static int seleccion() {
        System.out.println("""
            \n---------------------------------------------
                Seleccione el resultado que desea obtener:

                1. Matriz escalonada superior
                2. Matriz escalonada inferior
                3. Matriz identidad
                4. Volver al menú principal.""");
        return leerEntero();
    }

    private static double[][] ingresarMatriz() {
        double[][] matriz = new double[3][3];
        System.out.println("\nIngrese los valores de la matriz 3x3:");

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("Valor en la posición (%d, %d):%n", i + 1, j + 1);
                matriz[i][j] = leerDouble();
            }
        }

        return matriz;
    }

    static double[][] matriz_superior(double[][] entrada) {
        double[][] matriz = copiarMatriz(entrada);
        int n = matriz.length;

        for (int columna = 0; columna < n; columna++) {
            int pivote = buscarPivote(matriz, columna, columna, n - 1);
            if (pivote == -1) {
                continue;
            }
            intercambiarFilas(matriz, columna, pivote);

            for (int fila = columna + 1; fila < n; fila++) {
                double factor = matriz[fila][columna] / matriz[columna][columna];
                matriz[fila][columna] = 0;
                for (int j = columna + 1; j < n; j++) {
                    matriz[fila][j] -= factor * matriz[columna][j];
                }
            }
        }

        limpiarCeros(matriz);
        return matriz;
    }

    static double[][] matriz_inferior(double[][] entrada) {
        double[][] matriz = copiarMatriz(entrada);
        int n = matriz.length;

        for (int columna = n - 1; columna >= 0; columna--) {
            int pivote = buscarPivote(matriz, columna, 0, columna);
            if (pivote == -1) {
                continue;
            }
            intercambiarFilas(matriz, columna, pivote);

            for (int fila = 0; fila < columna; fila++) {
                double factor = matriz[fila][columna] / matriz[columna][columna];
                matriz[fila][columna] = 0;
                for (int j = 0; j < columna; j++) {
                    matriz[fila][j] -= factor * matriz[columna][j];
                }
            }
        }

        limpiarCeros(matriz);
        return matriz;
    }

    static double[][] matriz_identidad(double[][] entrada) {
        double[][] matriz = copiarMatriz(entrada);
        int n = matriz.length;

        for (int columna = 0; columna < n; columna++) {
            int pivote = buscarPivote(matriz, columna, columna, n - 1);
            if (pivote == -1) {
                return null;
            }
            intercambiarFilas(matriz, columna, pivote);

            double valorPivote = matriz[columna][columna];
            for (int j = 0; j < n; j++) {
                matriz[columna][j] /= valorPivote;
            }

            for (int fila = 0; fila < n; fila++) {
                if (fila == columna) {
                    continue;
                }
                double factor = matriz[fila][columna];
                for (int j = 0; j < n; j++) {
                    matriz[fila][j] -= factor * matriz[columna][j];
                }
            }
        }

        limpiarCeros(matriz);
        return matriz;
    }

    private static int buscarPivote(double[][] matriz, int columna, int primeraFila, int ultimaFila) {
        int pivote = -1;
        double mayorValor = EPSILON;

        for (int fila = primeraFila; fila <= ultimaFila; fila++) {
            double valor = Math.abs(matriz[fila][columna]);
            if (valor > mayorValor) {
                mayorValor = valor;
                pivote = fila;
            }
        }

        return pivote;
    }

    private static void intercambiarFilas(double[][] matriz, int fila1, int fila2) {
        double[] temporal = matriz[fila1];
        matriz[fila1] = matriz[fila2];
        matriz[fila2] = temporal;
    }

    private static double[][] copiarMatriz(double[][] entrada) {
        if (entrada.length == 0) {
            throw new IllegalArgumentException("La matriz no puede estar vacía.");
        }

        int n = entrada.length;
        double[][] copia = new double[n][n];
        for (int i = 0; i < n; i++) {
            if (entrada[i].length != n) {
                throw new IllegalArgumentException("La matriz debe ser cuadrada.");
            }
            copia[i] = entrada[i].clone();
        }
        return copia;
    }

    private static void limpiarCeros(double[][] matriz) {
        for(double[] matriz1 : matriz) {
            for (int j = 0; j < matriz1.length; j++) {
                if (Math.abs(matriz1[j]) < EPSILON) {
                    matriz1[j] = 0;
                }
            }
        }
    }

    private static void imprimirMatriz(double[][] matriz) {
        for (double[] fila : matriz) {
            for (double valor : fila) {
                System.out.printf("%10.4f", valor);
            }
            System.out.println();
        }
    }

    private static int leerEntero() {
        while (true) {
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble() {
        while (true) {
            String entrada = sc.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }
}
