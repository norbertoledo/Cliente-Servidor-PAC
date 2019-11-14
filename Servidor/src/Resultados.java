import java.util.ArrayList;

public class Resultados {

    // Listado de posiciones finales de la carrera
    //public static ArrayList<String> posiciones = new ArrayList<String>();
    public static ArrayList<Tortuga> posiciones = new ArrayList<Tortuga>();


    public Resultados(){ }

    // Devoler el listado de posiciones
    public static ArrayList<Tortuga> getPosiciones(){
        return posiciones;
    }

    // Agregar el nombre de tortuga al listado de posiciones cuando llega a la meta
    public static synchronized void setPosiciones(Tortuga tortuga){
        posiciones.add(tortuga);
    }

    // Resetear el listado de posiciones
    public static void resetPosiciones(){
        posiciones.clear();
    }
}
