import java.util.Arrays;
import java.util.Scanner;

public class matricesEleccion {

    // Version base hecha por Yassell:
    static Scanner leer = new Scanner(System.in);
    static double[][] matrizPrincipal = new double[3][3];

    public static void main(String[] args) {
        elecciones();
        System.out.println(devolverMatriz());
    }

    static void elecciones() {
        System.out.println("""
            Bienvenido al programa sobre nuestro último trabajo en algebra lineal.

            Ingrese el número de la opción que desea realizar:
            1. Factorizacion LxU.
            2. Transformaciones elementales.
            """);
        //Creo que te falta el scanner para recibir los datos
    }

    static String devolverMatriz(){
        for(int i = 0; i < matrizPrincipal.length; i++){
            for(int j = 0; j < matrizPrincipal[i].length; j++){
                System.out.println("Ingrese el valor en la posición: " + (i+1) + ", " + (j+1));
                matrizPrincipal[i][j] = leer.nextDouble();
            }
        }

        String matriz = "";
        for (double[] matrizP : matrizPrincipal) {
            matriz += Arrays.toString(matrizP) + "\n";
        }

        return matriz;
    }
    // Final de la base.

}
