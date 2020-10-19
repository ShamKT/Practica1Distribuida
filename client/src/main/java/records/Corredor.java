package records;

import java.io.Serializable;
import java.util.Arrays;

public class Corredor implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4895473332089945967L;

    private String nombre;
    private byte[] passHash;
    private String estado;
    private String genero;
    private int edad;

    public Corredor(String nombre, byte[] passHash, String estado, String genero, int edad) {
        this.nombre = nombre;
        this.passHash = passHash;
        this.estado = estado;
        this.genero = genero;
        this.edad = edad;
    }


    public String getNombre() {
        return nombre;
    }


    public byte[] getPassHash() {
        return passHash;
    }


    public String getEstado() {
        return estado;
    }


    public String getGenero() {
        return genero;
    }


    public int getEdad() {
        return edad;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Corredor) {
            Corredor otro = (Corredor) obj;
            if (this.nombre.equals(otro.getNombre()) && Arrays.equals(this.passHash, otro.getPassHash())) {
                return true;
            }
        }
        return false;
    }
}
