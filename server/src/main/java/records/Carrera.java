package records;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Active Record que modela a una carrera, no contiene llave primaria por simplicidad.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class Carrera implements Serializable {

    private static final long serialVersionUID = 4134597379516183363L;

    private String corredor;
    private String tipo;
    private String tiempo;
    private int distancia;
    private Date fecha;
    private String fechaString;

    /**
     * Constructor de un objeto carrera.
     * 
     * @param corredor
     *     El nombre del corredor que participo en la carrera.
     * @param tipo
     *     El tipo de carrera.
     * @param tiempo
     *     El tiempo en formato "hh:mm:ss.cc".
     * @param distancia
     *     La distancia de la carrera.
     * @param fecha
     *     La fecha de la carrera.
     */
    public Carrera(String corredor, String tipo, String tiempo, int distancia, Date fecha) {
        this.corredor = corredor;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.distancia = distancia;
        this.fecha = fecha;
        this.fechaString = fecha.toString();
    }


    /**
     * Metodo que añade el objeto que lo ejecuta en la base de datos.
     */
    public void insert() {
        try {
            PreparedStatement insert = ConexionMySQL.getInstance().getConnection().prepareStatement(
                    "INSERT INTO carrera (corredor, tipo, tiempo, distancia, fecha) VALUES (?, ?, ?, ?, ?)");
            insert.setString(1, corredor);
            insert.setString(2, tipo);
            insert.setString(3, tiempo);
            insert.setInt(4, distancia);
            insert.setDate(5, fecha);

            insert.execute();
            System.out.println("Carrera añadida a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Metodo que recupera todas las carreras de la base de datos o las realizadas por un corredor si se especifica y
     * las añade en una lista.
     * 
     * @param corredor
     *     El nombre del corredor del que se buscaran carreras, null para obtener todas las carreras.
     * @return Una lista con las carreras solicitadas.
     */
    public static LinkedList<Carrera> select(String corredor) {
        try {
            PreparedStatement select;
            if (corredor == null) {
                select = ConexionMySQL.getInstance().getConnection().prepareStatement("SELECT * FROM carrera");
            } else {
                select = ConexionMySQL.getInstance().getConnection()
                        .prepareStatement("SELECT * FROM carrera WHERE corredor = ?");
                select.setString(1, corredor);
            }

            ResultSet res = select.executeQuery();

            LinkedList<Carrera> lista = new LinkedList<>();

            while (res.next()) {
                lista.add(new Carrera(res.getString(2), res.getString(3), res.getString(4), res.getInt(5),
                        res.getDate(6)));
            }

            System.out.println("Se generó una lista de carreras.");
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getCorredor() {
        return corredor;
    }


    public void setCorredor(String corredor) {
        this.corredor = corredor;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getTiempo() {
        return tiempo;
    }


    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }


    public int getDistancia() {
        return distancia;
    }


    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }


    public Date getFecha() {
        return fecha;
    }


    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public String getFechaString() {
        return fechaString;
    }


    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

}
