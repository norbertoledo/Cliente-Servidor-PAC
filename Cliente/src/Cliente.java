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
    public static String mensajeParaElServidor;
    private Menu menu;


    public Cliente() throws IOException {

        // Crear la conexion con el servidor
        socket = new Socket(HOST, PUERTO);

        // Imprime mensaje de inicio
        System.out.println("Cliente Iniciado...");

    }

    public void iniciarCliente() {

        try {

            // Primer mensaje de conexion con el Servidor
            mensajeParaElServidor = "Cliente conectado!";

            // Construir el flujo de entrada del cliente
            mensajeAlCliente = new DataInputStream(socket.getInputStream());

            // Construir el flujo de salida del cliente
            mensajeDelCliente = new DataOutputStream(socket.getOutputStream());


            while (true) {

                // Enviar mensajes al servidor
                mensajeDelCliente.writeUTF(mensajeParaElServidor);

                // Recibir mensajes del servidor y enviar al parser el dato en string
                parsearDatoServidor(mensajeAlCliente.readUTF());

            }

        } catch (IOException e) {
            // Desconectar
            System.out.println("Desconectado de Servidor\n\n");
        } finally {
            // Imprimir mensaje de finalización de la App
            System.out.println("Gracias por utilizar");
            System.out.println(TituloApp.getTitulo());
            System.out.println("Hasta Pronto!\n");
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

        // Intanciar el menu de opciones
        if(menu == null) {
            menu = new Menu();
        }
        menu.espacio();
        // Muestra el menu de opciones
        menu.mostrarMenu();

    }

}
