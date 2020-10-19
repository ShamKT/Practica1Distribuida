package server;

import records.Corredor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws IOException {

        String op = ex.getRequestURI().getQuery();
        int code = -1;
        String respuesta = "";

        ObjectInputStream is = new ObjectInputStream(ex.getRequestBody());
        Corredor corredor = null;
        try {
            corredor = (Corredor) is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        switch (op) {
            case "reg": {
                if (Corredor.select(corredor.getNombre()) != null) {
                    code = 409;
                    respuesta = "El nombre de usuario ya existe.";
                } else {
                    corredor.insert();
                    code = 200;
                    respuesta = generarToken(corredor.getNombre());
                }
                break;
            }
            case "aut": {
                if (Corredor.select(corredor.getNombre()) == null) {
                    code = 401;
                    respuesta = "El nombre de usuario o la contraseña son incorrectos.";
                } else {
                    Corredor autenticando = Corredor.select(corredor.getNombre());
                    if (Arrays.equals(autenticando.getPassHash(), corredor.getPassHash())) {
                        code = 200;
                        respuesta = generarToken(corredor.getNombre());
                    } else {
                        code = 401;
                        respuesta = "El nombre de usuario o la contraseña son incorrectos.";
                    }
                }
                break;
            }
        }

        byte[] bs = respuesta.getBytes("UTF-8");
        ex.sendResponseHeaders(code, bs.length);
        OutputStream os = ex.getResponseBody();
        os.write(bs);
        os.flush();
        os.close();

    }


    private String generarToken(String usuario) {
        Signer signer = HMACSigner.newSHA256Signer("too many secrets");

        JWT jwt = new JWT().setIssuer("localhost").setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC)).setSubject(usuario)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(1));

        return JWT.getEncoder().encode(jwt, signer);
    }

}
