import java.util.Random;

// La clase tortuga implementa la interfaz Runnable
public class Tortuga implements Runnable {

    private String nombre;
    private String dorsal;
    private int meta;
    private int velocidad;
    private int velocidadMaxima;


    public Tortuga(){

    }

    // Devolver el nombre de la toruga
    public String getNombre() {
        return nombre;
    }

    // Asignar el nombre de la toruga
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devolver la dorsal de la toruga
    public String getDorsal() {
        return dorsal;
    }

    // Asignar la dorsal de la toruga
    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    // Sobreescribir el metodo run para iniciar su ejecucion
    @Override
    public void run(){

        // Setear los parametros de carrera
        this.meta = 500;
        this.velocidadMaxima = 50;
        this.velocidad = getRand();

        // Imprimir el inicio de carrera en el servidor
        System.out.println(nombre+" comenzó la carrera");

        for( int i = 0; i < meta; i += velocidad){

            // Imprimir cada avance de la tortuga en el servidor
            System.out.println(this.nombre+", con dorsal número "+this.dorsal+" recorrió "+i+" metros.");

            // Variar la velocidad en cada pasada del bucle
            velocidad = getRand();
        }

        // Finalizar la carrera una vez que llega a la meta
        finaliza();
    }

    // Devolver un valor aleatorio entre 1 y velocidadMaxima
    public int getRand(){
        Random rand = new Random();
        return rand.nextInt((velocidadMaxima - 1) + 1) + 1;
    }

    // Finalizar la carrera de la tortuga
    public void finaliza(){

        // Imprimir el aviso en el servidor de quien acaba de finalizar la carrera
        System.out.println(nombre + " finalizó la carrera");

        // Setear su nombre en el listados de posiciones
        Resultados.setPosiciones(this.getNombre());
    }
}
