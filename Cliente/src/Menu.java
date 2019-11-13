import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    // Instancia del Scanner
    static Scanner sc = new Scanner( System.in );

    // Instancia de las opciones del Menu
    private static ArrayList<String> opciones = new ArrayList<String>();

    // Objetos de E/S que la clase recibe por parámetros
    private DataOutputStream mensajeDelCliente;
    private DataInputStream mensajeAlCliente;


    public Menu() {

        // Agregar la opciones al listado del Menu
        opciones.add("Crear Tortuga");
        opciones.add("Eliminar Tortuga");
        opciones.add("Listar Tortugas");
        opciones.add("Iniciar Carrera");
        opciones.add("Salir de la App");


        espacio(2);

        // Titulo de la App
        System.out.println(TituloApp.getTitulo());

        espacio();
    }



    // Generador de salto de linea
    public static void espacio () {
        System.out.println("");
    }

    // Sobrecarga de Generador de salto de linea
    public static void espacio(int cantidad) {
        for (int i = 0; i<cantidad; i++ ) {
            System.out.println("");
        }
    }

    // Construir y mostrar el Menu principal
    protected void mostrarMenu(DataOutputStream mensajeDelCliente, DataInputStream mensajeAlCliente) throws IOException {

        this.mensajeAlCliente = mensajeAlCliente;
        this.mensajeDelCliente = mensajeDelCliente;

        // Validar que la seleccion ingresada sea numerica y esté dentro de las opciones válidas.

        boolean error = false;
        int opcion=0;

        do{
            try{

                espacio();
                System.out.println("--------------------------");
                System.out.println("*  ¿QUE DESEA REALIZAR?  *");
                System.out.println("--------------------------");
                espacio();

                for(int i = 0; i< opciones.size();i++){
                    System.out.println("["+(i+1)+"] "+opciones.get(i));
                }

                opcion = sc.nextInt();
                sc.nextLine();

                if(opcion > opciones.size() || opcion <= 0 ){
                    error = true;
                    espacio();
                    System.out.println("- La selección no es válida. Vuelva a intentarlo -");
                    espacio();

                }else {
                    error = false;
                }

            }

            catch (InputMismatchException e) {
                error = true;
                espacio();
                System.out.println("- La selección no es válida. Vuelva a intentarlo -");
                espacio();
                sc.next();
            }

        }
        while(error);

        espacio();

        // Enviar la opcion seleccionada
        validarOpcion( String.valueOf(opcion) );
    }

    // Recibir la opcion seleccionada y llamar al metodo correspondiente
    private void validarOpcion(String opcion) throws IOException {

        switch (opcion) {
            case "1":
                crearTortuga(opcion);
                break;
            case "2":
                eliminarTortuga(opcion);
                break;
            case "3":
                listarTortugas(opcion);
                break;
            case "4":
                iniciarCarrera(opcion);
                break;
            case "5":
                salirApp(opcion);
                break;

        }

    }


    // 1 -> CREAR TORTUGA
    private void crearTortuga(String opcion) throws IOException {

        // Validar que ingrese los datos para el Nombre
        // Validar que ingrese los datos para la Dorsal (Alfanumerico)
        // Si no completa algun dato, vuelve a solicitarlo

        boolean error = false;
        String nombre = "";
        String dorsal = "";

        System.out.println("-------------------------------------------");
        System.out.println("*  Inserte los datos de la nueva Tortuga  *");
        System.out.println("-------------------------------------------");

        espacio();

        do {
            try{
                while(nombre.equals("")) {
                    System.out.print("Nombre: ");
                    nombre = sc.nextLine();
                }
                while(dorsal.equals("")) {
                    System.out.print("Dorsal: ");
                    dorsal = sc.nextLine();
                }
                error=false;
            }
            catch(Exception ex) {
                error = true;
                espacio();
                System.out.println("- Dato inválido. Vuelva a intentarlo -");
                espacio();
                sc.nextLine();
            }
        }while(error);

        // Concatenar los datos ingresados
        String data = opcion+"|"+nombre+"|"+dorsal;

        // Llamar al método encargado de envío de datos al servidor
        enviarOpcion(data);

    }

    // 2 -> ELIMINAR TORTUGA
    private void eliminarTortuga(String opcion) throws IOException {

        // Validar que el dato ingresado sea numerico
        // Si no completa algun dato, vuelve a solicitarlo

        boolean error = false;
        int id = 0;

        System.out.println("----------------------------------------");
        System.out.println("*  Indique que Tortuga desea eliminar  *");
        System.out.println("----------------------------------------");

        espacio();

        do {
            try{

                    System.out.print("Número: ");
                    id = sc.nextInt();
                    sc.nextLine();

                error=false;
            }
            catch(InputMismatchException ex) {
                error = true;
                espacio();
                System.out.println("- Dato inválido. Vuelva a intentarlo -");
                espacio();
                sc.next();
            }
        }while(error);

        // Concatenar los datos ingresados
        String data = opcion+"|"+id;

        // Llamar al método encargado de envío de datos al servidor
        enviarOpcion(data);
    }

    // 3 -> LISTAR TORTUGAS
    private void listarTortugas(String opcion) throws IOException {
        // Llamar al método encargado de envío de datos al servidor
        enviarOpcion(opcion);
    }

    // 4 -> INICIAR CARRERA
    private void iniciarCarrera(String opcion) throws IOException {
        // Llamar al método encargado de envío de datos al servidor
        enviarOpcion(opcion);
    }

    // 5 -> SALIR DE LA APLICACION
    private void salirApp(String opcion) throws IOException {
        // Llamar al método encargado de envío de datos al servidor
        enviarOpcion(opcion);
    }

    // Método encargado de envío de datos al servidor
    private void enviarOpcion(String data) throws IOException {
        // Envia siempre un String
        this.mensajeDelCliente.writeUTF(data);
    }

}
