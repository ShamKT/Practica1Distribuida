package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

import records.Carrera;
import records.Corredor;

/**
 * Clase cliente que controla la comunicación con el servidor.
 * 
 * @author Orlando Ledesma Rincon
 *
 */
public class ClientHTTP {

    /**
     * Metodo que envia una petición GET al servidor para registar o autenticar a un usuario. En caso de completarse
     * satisfactoriamente registra al usuario y al JWT que devuelve el servidor en la instancia de la clase Sesion.
     * 
     * @param op
     *     La operación que se va a realizar: "reg" para registar un usuario nuevo, "aut" para autenticar un usuario.
     * @param user
     *     El nombre del usuario a registrar o autenticar.
     * @param pass
     *     La contraseña que ingresa el usuario.
     * @param estado
     *     El estado de residencia del usuario que se va a registrar, null si se esta autenticando.
     * @param genero
     *     El género del usuario que se va a registrar, null si se esta autenticando.
     * @param edad
     *     La edad del usuario a registrar, null si se esta autenticando.
     * @return El codigo de respuesta del servidor o -1 si no se logra conectar con el servidor.
     */
    public int sendGetLogin(String op, String user, String pass, String estado, String genero, int edad) {
        MessageDigest md = null;
        Corredor corredor = null;
        int responseCode;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        md.update(pass.getBytes(StandardCharsets.UTF_8));

        corredor = new Corredor(user, md.digest(), estado, genero, edad);
        md.reset();

        String url = "http://localhost:8000/login" + "?" + op;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            try (ObjectOutputStream wr = new ObjectOutputStream(connection.getOutputStream())) {
                wr.writeObject(corredor);
                wr.flush();
            }

            responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String respuesta = in.readLine();

                Sesion.GetInstance().setToken(respuesta);
                Sesion.GetInstance().setCorredor(corredor);
            }

        } catch (ConnectException e) {
            return -1;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return -2;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -3;
        } catch (IOException e) {
            e.printStackTrace();
            return -4;
        }
        return responseCode;
    }


    /**
     * Metodo que envia una petición GET al servidor con un objeto carrera para registrarlo en la base de datos.
     * 
     * @param carrera
     *     La carrera que se va a registrar.
     * @return El codigo de respuesta del servidor o -1 si no se logra conectar con el servidor.
     */
    public int sendGetAnadir(Carrera carrera) {

        String url = "http://localhost:8000/api?add";
        HttpURLConnection connection = null;
        int responseCode;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + Sesion.GetInstance().getToken());
            connection.setDoOutput(true);

            try (ObjectOutputStream wr = new ObjectOutputStream(connection.getOutputStream())) {
                wr.writeObject(carrera);
                wr.flush();
            }

            responseCode = connection.getResponseCode();

        } catch (ConnectException e) {
            return -1;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return -2;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -3;
        } catch (IOException e) {
            e.printStackTrace();
            return -4;
        }
        return responseCode;

    }


    /**
     * Metodo que envia una solicitud GET al servidor para obtener una lista de carreras para llenar la bitácora
     * personal o general, según se requiera.
     * 
     * @param tipo
     *     El tipo de bitacora que se requiere: "P" si es la bitácora personal, "G" para la bitácora general.
     * @param corredor
     *     El corredor del que se requiere la bitácora personal, null si requiere la bitacora general
     * @return Lista con las carreras para la bitácora.
     */
    @SuppressWarnings("unchecked")
    public LinkedList<Carrera> sendGetBitacora(String tipo, Corredor corredor) {

        String url = "http://localhost:8000/api?bitacora" + tipo;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + Sesion.GetInstance().getToken());
            connection.setDoOutput(true);

            try (ObjectOutputStream wr = new ObjectOutputStream(connection.getOutputStream())) {
                wr.writeObject(corredor);
                wr.flush();
            }

            int responseCode = connection.getResponseCode();
            LinkedList<Carrera> lista = null;

            if (responseCode == 200) {
                ObjectInputStream is = new ObjectInputStream(connection.getInputStream());

                Object obj = is.readObject();

                if (obj instanceof LinkedList<?>)
                    lista = (LinkedList<Carrera>) obj;
                return lista;
            } else {
                return null;
            }

        } catch (ConnectException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
