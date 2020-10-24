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

/**
 * Clase Handler para la contexto "/login". En este se realiza el registro y el inicio de sesion de usuarios.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class LoginHandler implements HttpHandler {

    /**
     * Metodo handle de la clase, obtiene el tipo de operación y registra o verifica la información de inicio de sesion.
     * En caso de de intentar registrar un usuario con un nombre ya utilizado envia un codigo 409 de respuesta, caso
     * contrario devuelve un codigo 200. En caso de que la infromación de inicio de sesion sea incorrecta devuelve un
     * código 401, caso contrario envia un código 200 en la respuesta.
     */
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
                    System.out.println("Se inicio sesión con el usuario " + corredor.getNombre() + ".");
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
                        System.out.println("Se inicio sesión con el usuario " + corredor.getNombre() + ".");
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


    /**
     * Método que genera un JWT y lo codifica.
     * 
     * @param usuario
     *     El nombre del usuario al que se asocia el token.
     * @return Una cadena que contiene el token codificado.
     */
    private String generarToken(String usuario) {
        Signer signer = HMACSigner.newSHA256Signer("too many secrets");

        JWT jwt = new JWT().setIssuer("localhost").setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC)).setSubject(usuario)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(1));

        return JWT.getEncoder().encode(jwt, signer);
    }

}
