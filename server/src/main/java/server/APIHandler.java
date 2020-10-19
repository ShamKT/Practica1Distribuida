package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import records.Carrera;
import records.Corredor;

public class APIHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws IOException {

        String op = ex.getRequestURI().getQuery();
        int code = -1;
        String respuesta = "";
        
        String authorization = ex.getRequestHeaders().getFirst("Authorization");

        
        if (authorization == null || !verificarToken(authorization)) {
            code = 403;
            respuesta = "Prohibido";
            byte[] bs = respuesta.getBytes("UTF-8");
            ex.sendResponseHeaders(code, bs.length);
            return;
        }

        switch (op) {
            case "add": {
                ObjectInputStream is = new ObjectInputStream(ex.getRequestBody());
                Carrera carrera = null;
                try {
                    carrera = (Carrera) is.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                is.close();
                carrera.insert();
                
                code = 200;
                byte[] bs = respuesta.getBytes("UTF-8");
                ex.sendResponseHeaders(code, bs.length);


            }
            case "bitacoraP": {
                ObjectInputStream is = new ObjectInputStream(ex.getRequestBody());
                Corredor corredor = null;
                try {
                    corredor = (Corredor) is.readObject();
                    
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                is.close();


                LinkedList<Carrera> lista = Carrera.select(corredor.getNombre());

                code = 200;
                byte[] bs = respuesta.getBytes("UTF-8");
                ex.sendResponseHeaders(code, bs.length);
                ObjectOutputStream os = new ObjectOutputStream(ex.getResponseBody());
                os.writeObject(lista);
                os.flush();
                os.close();




            }
            
            case "bitacoraG":{
                LinkedList<Carrera> lista = Carrera.select(null);
                
                code = 200;
                byte[] bs = respuesta.getBytes("UTF-8");
                ex.sendResponseHeaders(code, bs.length);
                ObjectOutputStream os = new ObjectOutputStream(ex.getResponseBody());
                os.writeObject(lista);
                os.flush();
                os.close();
                
            }
        }


    }


    private boolean verificarToken(String authorization) {
        String token = authorization.split(" ")[1];

        try {
            Verifier verifier = HMACVerifier.newVerifier("too many secrets");
            JWT.getDecoder().decode(token, verifier);
        } catch (JWTExpiredException e) {
            return false;
        }
        return true;
    }

}
