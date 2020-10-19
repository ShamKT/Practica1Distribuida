package server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Main {

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
