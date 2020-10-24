package records;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Active Record que modela a una corredor, no contiene ni llave primaria por simplicidad.
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


    /**
     * Metodo que añade el objeto que lo ejecuta en la base de datos.
     */
    public void insert() {
        try {
            PreparedStatement insert = ConexionMySQL.getInstance().getConnection().prepareStatement(
                    "INSERT INTO corredor (nombre, passhash, estado, genero, edad) VALUES (?, ?, ?, ?, ?)");
            insert.setString(1, nombre);
            insert.setBytes(2, passHash);
            insert.setString(3, estado);
            insert.setString(4, genero);
            insert.setInt(5, edad);

            insert.execute();
            System.out.println("Corredor añadido a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método que busca el corredor con el nombre especificado.
     * 
     * @param nombre
     *     El nombre de corredor a buscar.
     * @return El objeto corredor que coincida con el nombre o null si no se encuentra ninguno.
     */
    public static Corredor select(String nombre) {
        try {
            PreparedStatement select = ConexionMySQL.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM corredor WHERE nombre = ?");
            select.setString(1, nombre);
            ResultSet res = select.executeQuery();

            if (!res.next()) {
                return null;
            }

            String nombreRes = res.getString(2);
            byte[] passHashRes = res.getBytes(3);
            String estadoRes = res.getString(4);
            String generoRes = res.getString(5);
            int edadRes = res.getInt(6);

            return new Corredor(nombreRes, passHashRes, estadoRes, generoRes, edadRes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
