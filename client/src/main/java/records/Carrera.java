package records;

import java.io.Serializable;
import java.sql.Date;

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
