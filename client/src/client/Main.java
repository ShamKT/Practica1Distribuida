package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
    static Scene Login;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Login = new Scene(root);
        
        primaryStage.setScene(Login);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Registro de Carreras");
        primaryStage.show();   
    }

    public static void main(String[] args) {
        launch(args);
    }
}
