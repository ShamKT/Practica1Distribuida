package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Registro de Carreras");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
