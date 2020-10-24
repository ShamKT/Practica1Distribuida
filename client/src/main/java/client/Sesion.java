package client;

import records.Corredor;

/**
 * Singleto que contiene la información de la sesion activa.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class Sesion {

    public static Sesion instanceSesion = new Sesion();

    private String token;
    private Corredor corredor;

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public Corredor getCorredor() {
        return corredor;
    }


    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }


    private Sesion() {

    }


    public static Sesion GetInstance() {
        return instanceSesion;
    }

}
