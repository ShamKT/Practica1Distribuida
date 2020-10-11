package records;

import java.io.Serializable;
import java.util.Arrays;

public class Corredor implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2597213301703050526L;

    private String nombre;
    private byte[] passHash;

    public Corredor(String nombre, byte[] passHash) {
        this.nombre = nombre;
        this.passHash = passHash;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public byte[] getPassHash() {
        return passHash;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Corredor) {
            Corredor otro = (Corredor) obj;
            if (this.nombre.equals(otro.getNombre())) {
                return true;
            }           
        }
        return false;
    }
}
