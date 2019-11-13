import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    // Instancia del Scanner
    static Scanner sc = new Scanner( System.in );

    // Datos de conexión
    private final String HOST = "localhost";
    private final int PUERTO = 1234;
    private Socket socket;
    private DataInputStream mensajeAlCliente;
    private DataOutputStream mensajeDelCliente;
    private Menu menu;

    public Cliente() {

    }

    public void iniciarCliente() {

        try {

            // Intanciar el menu de opciones
            menu = new Menu();

            while (true) {

                // Crear la conexion con el servidor
                socket = new Socket(HOST, PUERTO);

                // Construir el flujo de entrada del cliente
                mensajeAlCliente = new DataInputStream(socket.getInputStream());

                // Construir el flujo de salida del cliente
                mensajeDelCliente = new DataOutputStream(socket.getOutputStream());

                // Enviar al parser el dato en string recibido del servidor
                parsearDatoServidor(mensajeAlCliente.readUTF());

                // Cerrar la conexion
                socket.close();

            }

        } catch (IOException e) {
            //System.out.println(e.getMessage());
            System.out.println("Desconectado de Servidor\n");

        } finally {
            // Imprimir mensaje de finalización de la App
            System.out.println("Gracias por utilizar");
            System.out.println(TituloApp.getTitulo());
            System.out.println("Hasta Pronto!");
        }
    }

    // Parsear el String recibido e imprime en el cliente el mensaje
    private void parsearDatoServidor(String datoServidor) throws IOException {

        menu.espacio();

        // Generar un array de datos en el caso que el string esté concatenado por la Expresión regular "pipe"
        String[] datos = datoServidor.split("\\|");

        // Recorrer e imprimir los mensaje en el cliente
        for(int i=0; i<datos.length;i++) {
            System.out.println(datos[i]);
        }

        menu.espacio();

        // Muestra el menu de opciones
        menu.mostrarMenu( mensajeDelCliente, mensajeAlCliente );

    }

}
