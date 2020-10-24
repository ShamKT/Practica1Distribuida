package server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * Clase principal del servidor.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class Main {

    /**
     * Método main de la clase. Inicia el servidor, crea los contextos "/login" y "/api" y les asocia sus handler.
     * @param args
     */
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/login", new LoginHandler());
            server.createContext("/api", new APIHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Servidor Iniciado");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
