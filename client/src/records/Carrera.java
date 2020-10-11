package records;

import java.io.Serializable;

public class Carrera implements Serializable{
    
    private int pk_carrera;
    private String tipo;
    private String tiempo;
    private int distancia;
    private String fecha;
    
    public Carrera(int pk_carrera, String tipo, String tiempo, int distancia) {
        
    }

}
