import java.util.Scanner;

public class matricesEleccion {
    // Version base hecha por Yassell:
    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        elecciones();
    }

    static void elecciones() {
        System.out.println("""
                    BIENVENIDO AL PROGRAMA DE MATRICES.
                    Seleccione una de las opciones disponibles:
                    1.
                    2.
                """);
        int opcion = leer.nextInt();

        switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                
        }
    }
    //Final de la base. XD
}
