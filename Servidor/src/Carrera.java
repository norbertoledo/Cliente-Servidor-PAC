import java.util.ArrayList;

public class Carrera {

    private ArrayList<Tortuga> tortugas = new ArrayList<Tortuga>();
    public boolean finCarrera;

    public Carrera(){

    }


    public void iniciarCarrera() {

        // Resetear el booleano de fin de carrera
        finCarrera = false;

        // Resetar del listado de posiciones
        Resultados.resetPosiciones();

        // Generar los hilos de la carrera
        this.generarHilos();

    }

    // Generar los hilos de la carrera
    public void generarHilos(){

        // Listado de Thread
        ArrayList<Thread> ts = new ArrayList<Thread>();

        // Agregar los Thread al listado y ejecutarlos
        for(int i=0; i<this.tortugas.size(); i++){
            Tortuga tortuga = this.tortugas.get(i);
            String nombre = tortuga.getNombre();
            Thread t =  new Thread(tortuga, nombre);
            ts.add(t);
            t.start();
        }

        // Unir los hilos para aguardar la finalizacion de todos
        for(int i=0; i<ts.size(); i++){
            try{
                ts.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Dar por finalizada la carrera cuando acaba la ejecucion de todos los hilos
        this.finCarrera = true;

    }


    // Devolver las posiciones finales de la carrera
    public ArrayList<Tortuga> getResultados(){
        ArrayList<Tortuga> posiciones = Resultados.getPosiciones();
        return posiciones;
    }


    // Crear las instancias de las tortugas, setear sus propiedades y devolver la nueva tortuga
    public Tortuga setTortuga(String nombre, String dorsal){
        Tortuga tortuga = new Tortuga();
        tortuga.setNombre(nombre);
        tortuga.setDorsal(dorsal);
        setTortugas(tortuga);
        return tortuga;
    }

    // Devolver una tortuga
    public Tortuga getTortuga(int id){
        Tortuga tortuga = tortugas.get(id);
        return tortuga;
    }

    // Eliminar una tortuga
    public void eliminarTortuga(int id){
        tortugas.remove(id);
    }

    // Devolver el listado de tortugas
    public ArrayList<Tortuga> getTortugas(){
        return tortugas;
    }

    // Agregar una tortuga al listado de Tortugas
    public void setTortugas(Tortuga tortuga){
        tortugas.add(tortuga);
    }

}
