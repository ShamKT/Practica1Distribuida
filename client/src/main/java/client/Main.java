package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal del cliente, contiene el metodo main.
 * 
 * @author Orlando Ledesma Rincón
 *
 */

public class Main extends Application {

    static Scene scene;

    /**
     * Método que inicializa la interfaz de usuario.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../GUI.fxml"));
        scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Registro de Carreras");
        primaryStage.show();
    }


    /**
     * Método main del cliente, inicia la aplicación.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
