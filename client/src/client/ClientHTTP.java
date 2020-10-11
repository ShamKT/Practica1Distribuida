package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import records.Carrera;
import records.Corredor;

public class ClientHTTP {

    public String sendGet(String context, Corredor corredor, String op) {
        String url = "http://localhost:8000" + context + "?" + op;
        int responseCode = -1;
        String respuesta = "";

        System.out.println("Creando conexion");
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            System.out.println("set Get");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            System.out.println("Escribiendo el OutputStream");
            try (ObjectOutputStream wr = new ObjectOutputStream(connection.getOutputStream())) {
                wr.writeObject(corredor);

                wr.flush();

            }
            ;
            System.out.println("getResponseCode");

            responseCode = connection.getResponseCode();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            respuesta = in.readLine();

            

            System.out.println(responseCode);
            System.out.println(respuesta);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return respuesta;
    }


    public void sendPost(String context, Carrera carrera) {

    }
}