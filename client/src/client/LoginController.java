package client;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import records.Corredor;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtNUser;

    @FXML
    private PasswordField txtNPass;

    @FXML
    private PasswordField txtNRPass;

    @FXML
    private Label lblPass;

    @FXML
    private Button btnRegister;

    @FXML
    private Label lblNError;

    private ClientHTTP client = new ClientHTTP();

    private MessageDigest md;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtNUser.setOnKeyReleased(e -> {
            if (txtNUser.getText().length() == 0 || txtNPass.getText().length() == 0
                    || txtNRPass.getText().length() == 0) {
                btnRegister.setDisable(true);
            } else {
                btnRegister.setDisable(false);
            }
        });

        txtNPass.setOnKeyReleased(e -> {
            if (txtNUser.getText().length() == 0 || txtNPass.getText().length() == 0
                    || txtNRPass.getText().length() == 0) {
                btnRegister.setDisable(true);
            } else {
                if (txtNRPass.getText().equals(txtNPass.getText())) {
                    lblPass.setVisible(false);
                    btnRegister.setDisable(false);
                } else {
                    lblPass.setVisible(true);
                    btnRegister.setDisable(true);
                }
            }
        });

        txtNRPass.setOnKeyReleased(e -> {
            if (txtNUser.getText().length() == 0 || txtNPass.getText().length() == 0
                    || txtNRPass.getText().length() == 0) {
                btnRegister.setDisable(true);
            } else {
                if (txtNRPass.getText().equals(txtNPass.getText())) {
                    lblPass.setVisible(false);
                    btnRegister.setDisable(false);
                } else {
                    lblPass.setVisible(true);
                    btnRegister.setDisable(true);
                }
            }
        });

        txtUser.setOnKeyReleased(e -> {
            if (txtUser.getText().length() == 0 || txtPass.getText().length() == 0) {
                btnLogin.setDisable(true);
            } else {
                btnLogin.setDisable(false);
            }
        });

        txtPass.setOnKeyReleased(e -> {
            if (txtUser.getText().length() == 0 || txtPass.getText().length() == 0) {
                btnLogin.setDisable(true);
            } else {
                btnLogin.setDisable(false);
            }
        });

        btnRegister.setOnAction(e -> Register());
        btnLogin.setOnAction(e -> login());
    }


    private void Register() {
        try {
            String user = txtNUser.getText();
            String pass = txtNPass.getText();

            md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));

            Corredor corredor = new Corredor(user, md.digest());
            md.reset();

            String respuesta = client.sendGet("/login", corredor, "reg");

            if (respuesta.length() > 100) {
                Sesion.GetInstance().setToken(respuesta);
                Sesion.GetInstance().setCorredor(corredor);
                cambiarScene();
            } else {
                lblNError.setText(respuesta);
            }

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void login() {
        try {
            String user = txtUser.getText();
            String pass = txtPass.getText();

            md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes(StandardCharsets.UTF_8));

            Corredor corredor = new Corredor(user, md.digest());
            md.reset();

            String respuesta = client.sendGet("/login", corredor, "aut");

            if (respuesta.length() > 100) {
                Sesion.GetInstance().setToken(respuesta);
                Sesion.GetInstance().setCorredor(corredor);
                cambiarScene();
            } else {
                lblError.setText(respuesta);
            }

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void cambiarScene() {
        Stage stage = (Stage) txtUser.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Main.fxml"))));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
}
