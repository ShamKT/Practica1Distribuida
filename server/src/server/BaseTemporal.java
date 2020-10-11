package server;

import java.util.LinkedList;

import records.Carrera;
import records.Corredor;

public class BaseTemporal {
    
    private static BaseTemporal instance = new BaseTemporal();
    
    private LinkedList<Corredor> corredores = new LinkedList<>();
    private LinkedList<Carrera> carreras = new LinkedList<>();

    
    private BaseTemporal() {
        
    }
    
    public static BaseTemporal getInstance() {
        return instance;
    }
    
    public void añadirCorredor(Corredor corredor) {
        corredores.add(corredor);
    }
    
    public void añadirCarrera(Carrera carrera) {
        carreras.add(carrera);
    }

    public LinkedList<Corredor> getCorredores() {
        return corredores;
    }

    public LinkedList<Carrera> getCarreras() {
        return carreras;
    }
    
    
}
