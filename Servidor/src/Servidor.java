import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    // Datos de conexión
    private final int PUERTO = 1234;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream mensajeDelServidor;
    private DataInputStream mensajeAlServidor;
    public String mensajeParaElCliente;


    private Carrera carrera = new Carrera();

    public Servidor() throws IOException {

        // Crear la conexion del servidor
        serverSocket = new ServerSocket(PUERTO);
        socket  = new Socket();

        // Imprime mensaje de inicio
        System.out.println("Servidor iniciado...");
    }

    public void iniciarServidor() {

        try{

            // Primer mensaje de conexion con el Cliente
            mensajeParaElCliente = "Conectado con el servidor!";

            System.out.println("Esperando la conexión del cliente...");

            // Queda a la espera de la conexion del cliente
            socket = serverSocket.accept();

            // Construir el flujo de entrada del servidor
            mensajeAlServidor = new DataInputStream( socket.getInputStream() );

            // Construir el flujo de salida del servidor
            mensajeDelServidor = new DataOutputStream( socket.getOutputStream() );

            while(true){

                // Recibir mensajes del cliente y enviar al parser el dato en string
                this.parsearDatoCliente( mensajeAlServidor.readUTF());

                // Enviar mensajes al cliente
                mensajeDelServidor.writeUTF( mensajeParaElCliente );

            }

        } catch (IOException | InterruptedException e) {
            // Desconectar
            System.out.println("\nCliente Desconectado\n");
        } finally {
            // Imprimir una mensaje de fin de conexion.
            System.out.println("FIN DE LA APP");
        }

    }


    // Parsear el String recibido del cliente y devolver un Array de datos
    private void parsearDatoCliente(String datoCliente) throws IOException, InterruptedException {
        // Generar un array de datos en el caso que el string esté concatenado por la Expresión regular "pipe"
        String[] datos = datoCliente.split("\\|");
        this.validarOpcion(datos);
    }

    // Validar la opcion seleccionada en el menu y llamar al metodo correspondiente
    private void validarOpcion(String[] datos) throws IOException, InterruptedException {
        switch (datos[0]) {
            case "1":
                crearTortuga(datos);
                break;
            case "2":
                eliminarTortuga(datos);
                break;
            case "3":
                listarTortugas(datos);
                break;
            case "4":
                iniciarCarrera(datos);
                break;
            case "5":
                salirApp(datos);
                break;
            default:
                System.out.println("\n"+datos[0]);
        }
    }


    // 1 -> CREAR TORTUGA
    private void crearTortuga(String[] datos) throws IOException {

        // Imprime solicitud del cliente
        System.out.println("\n\nCliente solicita: [" + datos[0] + "] Crear una Tortuga");

        // Enviar los datos parseados del cliente al metodo encargado de generar la instancia de la nueva tortuga
        Tortuga tortuga = carrera.setTortuga(datos[1], datos[2]);

        String mensaje = "";
        mensaje += "-----------------\n";
        mensaje += "* Crear Tortuga *\n";
        mensaje += "-----------------\n";
        mensaje += "La Tortuga "+tortuga.getNombre()+" con dorsal "+tortuga.getDorsal()+", se ha creado con éxito!";

        // Imprime mensaje en el servidor
        System.out.println(mensaje);

        // Envia mensaje al cliente
        mensajeParaElCliente = mensaje;

    }


    // 2 -> ELIMINAR TORTUGA
    private void eliminarTortuga(String[] datos) throws IOException {

        // Imprimir solicitud del cliente
        System.out.println("\n\nCliente solicita: [" + datos[0] + "] Eliminar una Tortuga");

        String mensaje = "";

        // Tomar el id que envió el cliente y castearlo a Integer
        int id = Integer.parseInt(datos[1])-1;

        // Validar si tortuga existe
        if(id < 0 || id >= carrera.getTortugas().size()){
            mensaje = "- El número de tortuga solicitada no existe. Vuelva a intentarlo -";
        }else {

            // Obtener el nombre de la tortuga
            String nombre = carrera.getTortuga(id).getNombre();

            // Llamar al metodo para eliminar una tortuga
            carrera.eliminarTortuga(id);

            mensaje += "--------------------\n";
            mensaje += "* Eliminar Tortuga *\n";
            mensaje += "--------------------\n";
            mensaje += "La Tortuga " + nombre + " ha sido eliminada con éxito!";

        }

        // Imprimir mensaje en el servidor
        System.out.println(mensaje);

        // Enviar mensaje al cliente
        mensajeParaElCliente = mensaje;
    }


    // 3 -> LISTAR TORTUGAS
    private void listarTortugas(String[] datos) throws IOException {

        // Imprimir solicitud del cliente
        System.out.println("\n\nCliente solicita: [" + datos[0] + "] Listar la Tortugas");

        String mensaje = "";
        mensaje += "-------------------\n";
        mensaje += "* Listar Tortugas *\n";
        mensaje += "-------------------\n";

        // Obtener las tortugas para generar el listado
        ArrayList<Tortuga> tortugas = carrera.getTortugas();

        if(tortugas.size()>0) {
            for (int i = 0; i < tortugas.size(); i++) {
                mensaje += "Tortuga: " + (i + 1) + " Nombre: " + tortugas.get(i).getNombre() + " y dorsal: " + tortugas.get(i).getDorsal();
                if(i<tortugas.size()-1) mensaje += "\n";
            }
        }else{
            // Generar mensaje de que no hay tortugas creadas
            mensaje += "No hay Tortugas creadas!";
        }

        // Imprimir mensaje en el servidor
        System.out.println(mensaje);

        // Enviar mensaje al cliente
        mensajeParaElCliente = mensaje;
    }


    // 4 -> INICIAR CARRERA
    private void iniciarCarrera(String[] datos) throws IOException, InterruptedException {

        // Imprimir solicitud del cliente
        System.out.println("\n\nCliente solicita: [" + datos[0] + "] Iniciar la Carrera");

        String mensaje = "";
        String mensajeInicio = "";
        String mensajeFin = "";
        mensajeInicio += "----------------------\n";
        mensajeInicio += "* Inician la Carrera *\n";
        mensajeInicio += "----------------------\n";

        // Obtener las tortugas para mostrar quienes compiten
        ArrayList<Tortuga> tortugas = carrera.getTortugas();

        if(tortugas.size()>1) {
            for (int i = 0; i < tortugas.size(); i++) {
                mensajeInicio += "Participante " +tortugas.get(i).getNombre()+" con dorsal "+tortugas.get(i).getDorsal()+"\n";
            }

            // Imprimir mensaje en el servidor - Inicio de Carrera
            System.out.println(mensajeInicio);

            // Iniciar la Carrera
            carrera.iniciarCarrera();

            // Evualuar el fin de la carrera - booleano de la clase Carrera
            while (carrera.finCarrera) {

                // Obtener las posiciones, recorrerlas y armar el string del resultado
                ArrayList<String> posiciones = carrera.getResultados();

                mensajeFin += "\n";
                mensajeFin += "---------------------\n";
                mensajeFin += "* Fin de la Carrera *\n";
                mensajeFin += "---------------------\n";
                mensajeFin += "¡¡¡ "+posiciones.get(0).toUpperCase() + " HA GANADO LA CARRERA !!!";

                // Bucle para imprimir en el cliente  las posiciones finales de la carrera
                /*
                for (int i = 0; i < posiciones.size(); i++) {
                    int puesto = (i + 1);
                    String nombre = posiciones.get(i);
                    mensajeFin += "\n Puesto " + puesto + " es para " + nombre;
                }
                */
                carrera.finCarrera = false;
            }
            // Imprimir mensaje en el servidor - Fin de Carrera
            System.out.println(mensajeFin);

        }else if(tortugas.size()==1){
            // Mensaje de que se necesitan al menos 2 Tortugas creadas para iniciar una carrera
            mensajeInicio += "- Se necesitan al menos 2 Tortugas creadas para iniciar una carrera! -";
        }else{
            // Mensaje de que no hay tortugas creadas para iniciar una carrera
            mensajeInicio += "- No hay Tortugas creadas para iniciar una carrera! -";
        }

        mensaje = mensajeInicio+mensajeFin;

        // Enviar mensaje al cliente
        mensajeParaElCliente = mensaje;

    }


    // 5 -> SALIR DE LA APLICACION
    private void salirApp(String[] datos) throws IOException {

        // Imprimir solicitud del cliente
        System.out.println("\n\nCliente solicita: [" + datos[0] + "] Salir de la App");

        // Cerrar la conexión de los Sockets
        socket.close();
        serverSocket.close();
    }
}