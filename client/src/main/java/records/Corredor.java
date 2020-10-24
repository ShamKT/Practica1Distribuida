package records;

import java.io.Serializable;

/**
 * Active Record que modela a una corredor, no contiene métodos CRUD ni llave primaria por simplicidad.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class Corredor implements Serializable {

    private static final long serialVersionUID = 4895473332089945967L;

    private String nombre;
    private byte[] passHash;
    private String estado;
    private String genero;
    private int edad;

    /**
     * Constructor de un objeto corredor.
     * 
     * @param nombre
     *     El nombre del corredor.
     * @param passHash
     *     El hash de la contraseña del corredor.
     * @param estado
     *     El estado de residencia del corredor.
     * @param genero
     *     El género del corredor.
     * @param edad
     *     La edad del corredor.
     */
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
}
