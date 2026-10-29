import java.util.Scanner;

public class matricesEleccion {

    // Version base hecha por Yassell:
    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int eleccion;

        do {
            eleccion = seleccion();
            switch (eleccion) {
                case 1 -> {
                    ingresarMatriz();
                }
                case 2 -> {
                    transformaciones_elementales.iniciar(leer);
                }
                case 3 -> {
                    System.out.println("Saliendo del programa...");
                }
                default -> {
                    System.out.println("Error en ingreso del dato solicitado, intentelo nuevamente");
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
        int opcion = leer.nextInt();

        return opcion;
    }

    static double[][] ingresarMatriz() {
        double[][] matrizIngresada = new double[3][3];

        for (int i = 0; i < matrizIngresada.length; i++) {
            for (int j = 0; j < matrizIngresada[i].length; j++) {
                System.out.println("Ingrese el valor en la posición: " + (i + 1) + ", " + (j + 1));
                matrizIngresada[i][j] = leer.nextDouble();
            }
        }

        return matrizIngresada;
    }
    // Final de la base.

}
