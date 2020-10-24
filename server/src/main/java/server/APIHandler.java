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

/**
 * Clase Handler para la contexto "/api". En este se realiza el registro de carreras y el acceso a la bitacora.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class APIHandler implements HttpHandler {

    /**
     * Método handle de la clase, verifica el JWT, obtiene el tipo de operación y, si el token no ha expirado, registra
     * una carrera o genera una lista de carreras según sea el caso. Envia un codigo 403 si el token ya expiro, caso
     * contrario la operacion se realiza y se envia un codigo 200 en la respuesta si se pudo realizar con exito o -1 si
     * no.
     */
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

                break;
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

                break;
            }

            case "bitacoraG": {
                LinkedList<Carrera> lista = Carrera.select(null);

                code = 200;
                byte[] bs = respuesta.getBytes("UTF-8");
                ex.sendResponseHeaders(code, bs.length);
                ObjectOutputStream os = new ObjectOutputStream(ex.getResponseBody());
                os.writeObject(lista);
                os.flush();
                os.close();

                break;
            }
        }

    }


    /**
     * Método que verifica si el token es valido.
     * 
     * @param authorization
     *     El token a verificar
     * @return true si el token aún no ha expirado, false en caso contrario.
     */
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
