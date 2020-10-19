package records;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Carrera implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4134597379516183363L;

    private String corredor;
    private String tipo;
    private String tiempo;
    private int distancia;
    private Date fecha;
    private String fechaString;

    public Carrera(String corredor, String tipo, String tiempo, int distancia, Date fecha) {
        this.corredor = corredor;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.distancia = distancia;
        this.fecha = fecha;
        this.fechaString = fecha.toString();
    }


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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
