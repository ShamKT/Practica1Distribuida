package records;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
