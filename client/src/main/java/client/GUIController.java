package client;

import java.net.URL;
import java.sql.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import records.Carrera;

/**
 * Clase que controla la interfaz de usuario del cliente.
 * 
 * @author Orlando Ledesma Rincón
 *
 */
public class GUIController implements Initializable {

    private ClientHTTP client = new ClientHTTP();

    @FXML
    private Pane pnLogin;

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
    private ComboBox<String> cmbEstado;

    @FXML
    private ComboBox<String> cmbGenero;

    @FXML
    private TextField txtEdad;

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

    @FXML
    private TabPane pnMenu;

    @FXML
    private AnchorPane apnCarrera;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private DatePicker dtpFecha;

    @FXML
    private TextField txtDistancia;

    @FXML
    private Spinner<Integer> spnHora;

    @FXML
    private Spinner<Integer> spnMinuto;

    @FXML
    private Spinner<Integer> spnSegundo;

    @FXML
    private Spinner<Integer> spnCentisegundo;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnRegistrarCarrera;

    @FXML
    private TableView<Carrera> tblPersonal;

    @FXML
    private Button btnActualizarP;

    @FXML
    private TableView<Carrera> tblGeneral;

    @FXML
    private Button btnActualizarG;

    Alert sesionAlert = new Alert(AlertType.INFORMATION);

    /**
     * Método que inicializa el controlador de la interfaz de usuario.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pnLogin.setVisible(true);

        pnLogin.setOnKeyReleased(e -> verificarLogin());
        pnLogin.setOnMouseMoved(e -> verificarLogin());

        cmbEstado.setItems(FXCollections.observableArrayList("Aguascalientes", "Baja California", "Baja California Sur",
                "Campeche", "Chiapas", "Chihuahuha", "Coahuila", "Colima", "Durango", "Estado de Mexico", "Guanajuato",
                "Guerrero", "Hidalgo", "Jalisco", "Michoacan", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla",
                "Querétaro", "Quintana Roo", "Sasn Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas",
                "Veracruz", "Yucatán", "Zacatecas"));

        cmbGenero.setItems(FXCollections.observableArrayList("Hombre", "Mujer", "Otro"));

        txtEdad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String anterior, String nuevo) {
                if (!nuevo.matches("\\d*"))
                    txtEdad.setText(nuevo.replaceAll("[^\\d]", ""));
            }
        });

        txtNPass.setOnKeyReleased(e -> {
            if (txtNRPass.getText().equals(txtNPass.getText())) {
                lblPass.setVisible(false);
            } else {
                lblPass.setVisible(true);
            }
        });

        txtNRPass.setOnKeyReleased(e -> {
            if (txtNRPass.getText().equals(txtNPass.getText())) {
                lblPass.setVisible(false);
            } else {
                lblPass.setVisible(true);
            }
        });

        btnRegister.setOnAction(e -> register());
        btnLogin.setOnAction(e -> login());

        apnCarrera.setOnKeyReleased(e -> verificarAnadir());
        apnCarrera.setOnMouseMoved(e -> verificarAnadir());

        cmbTipo.setItems(FXCollections.observableArrayList("Caminata", "Sprint", "Medio Fondo", "Fondo"));

        txtDistancia.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String anterior, String nuevo) {
                if (!nuevo.matches("\\d*"))
                    txtDistancia.setText(nuevo.replaceAll("[^\\d]", ""));
            }
        });

        btnRegistrarCarrera.setOnAction(e -> anadir());

        TableColumn<Carrera, String> clmTipoP = new TableColumn<>("Tipo");
        clmTipoP.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblPersonal.getColumns().add(clmTipoP);

        TableColumn<Carrera, String> clmTiempoP = new TableColumn<>("Tiempo");
        clmTiempoP.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        tblPersonal.getColumns().add(clmTiempoP);

        TableColumn<Carrera, Integer> clmDistanciaP = new TableColumn<>("Distancia");
        clmDistanciaP.setCellValueFactory(new PropertyValueFactory<>("distancia"));
        tblPersonal.getColumns().add(clmDistanciaP);

        TableColumn<Carrera, String> clmFechaP = new TableColumn<>("Fecha");
        clmFechaP.setCellValueFactory(new PropertyValueFactory<>("fechaString"));
        tblPersonal.getColumns().add(clmFechaP);

        TableColumn<Carrera, String> clmCorredorG = new TableColumn<>("Corredor");
        clmCorredorG.setCellValueFactory(new PropertyValueFactory<>("corredor"));
        tblGeneral.getColumns().add(clmCorredorG);

        TableColumn<Carrera, String> clmTipoG = new TableColumn<>("Tipo");
        clmTipoG.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblGeneral.getColumns().add(clmTipoG);

        TableColumn<Carrera, String> clmTiempoG = new TableColumn<>("Tiempo");
        clmTiempoG.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        tblGeneral.getColumns().add(clmTiempoG);

        TableColumn<Carrera, Integer> clmDistanciaG = new TableColumn<>("Distancia");
        clmDistanciaG.setCellValueFactory(new PropertyValueFactory<>("distancia"));
        tblGeneral.getColumns().add(clmDistanciaG);

        TableColumn<Carrera, String> clmFechaG = new TableColumn<>("Fecha");
        clmFechaG.setCellValueFactory(new PropertyValueFactory<>("fechaString"));
        tblGeneral.getColumns().add(clmFechaG);

        btnActualizarP.setOnAction(e -> llenarBitacoraP());
        btnActualizarG.setOnAction(e -> llenarBitacoraG());

        sesionAlert.setTitle("Error");
    }


    /**
     * Método que llama al metodo sendGetLogin de la clase cliente con la información que el usuario llena en la
     * interfaz para registrarse. En caso de obtener un codigo 200 cambia la interfaz al menu principal. En caso de un
     * codigo 409 o -1 envia el mensaje de alerta correspondiente al usuario.
     */
    private void register() {
        String user = txtNUser.getText();
        String pass = txtNPass.getText();
        String estado = cmbEstado.getValue();
        String genero = cmbGenero.getValue();
        int edad = Integer.parseInt(txtEdad.getText());
        int code = client.sendGetLogin("reg", user, pass, estado, genero, edad);

        if (code == 200) {
            pnLogin.setVisible(false);
            pnMenu.setVisible(true);
        } else if (code == 409) {
            sesionAlert.setHeaderText("El nombre de usuario ya existe.");
            sesionAlert.setContentText("Intentelo con un nombre distinto.");
            sesionAlert.show();
        } else if (code == -1) {
            sesionAlert.setHeaderText("No se pudo conectar con el servidor");
            sesionAlert.setContentText("Intentelo de nuevo, mas tarde.");
            sesionAlert.show();
        }

    }


    /**
     * Método que llama al metodo sendGetLogin de la clase cliente con la infromacion que el usuario llena en la
     * interfaz para iniciar sesion. En caso de obtener un codigo 200 cambia la interfaz al menu principal. En caso de
     * un codigo 401 o -1 envia el mensaje de alerta correspondiente al usuario.
     */
    private void login() {
        String user = txtUser.getText();
        String pass = txtPass.getText();
        int code = client.sendGetLogin("aut", user, pass, "", "", 0);

        if (code == 200) {
            pnLogin.setVisible(false);
            pnMenu.setVisible(true);
            txtUser.setText("");
            txtPass.setText("");
        } else if (code == 401) {
            sesionAlert.setHeaderText("El nombre de usuario o la contrase\u00f1a son incorrectos.");
            sesionAlert.setContentText("Intentelo de nuevo");
            sesionAlert.show();
        } else if (code == -1) {
            sesionAlert.setHeaderText("No se pudo conectar con el servidor");
            sesionAlert.setContentText("Intentelo de nuevo, mas tarde.");
            sesionAlert.show();
        }

    }


    /**
     * Método que llama al metodo sendGetAñadir de la clase cliente con la información que el usuario llena en la
     * interfaz para añadir una carrera al registro. En caso de recibir un codigo 200 limpia la interfaz para permitir
     * que el usuario introdusca los datos de otra carrera. En caso de recibir un codigo 403 envia el mensaje de alerta
     * correspondiente y regresa a la pantalla de inicio de sesion.
     */
    private void anadir() {
        String tipo = cmbTipo.getValue();
        Date fecha = Date.valueOf(dtpFecha.getValue());
        int distancia = Integer.parseInt(txtDistancia.getText());
        String tiempo = Integer.toString(spnHora.getValue()) + ":" + Integer.toString(spnMinuto.getValue()) + ":"
                + Integer.toString(spnSegundo.getValue()) + "." + Integer.toString(spnCentisegundo.getValue());
        int code = client.sendGetAnadir(
                new Carrera(Sesion.GetInstance().getCorredor().getNombre(), tipo, tiempo, distancia, fecha));

        if (code == 200) {
            sesionAlert.setHeaderText("Registro agregado satisfactoriamente");
            sesionAlert.setContentText("");
            sesionAlert.show();

            cmbTipo.setValue(null);
            dtpFecha.setValue(null);
            txtDistancia.setText("");
            spnHora.getValueFactory().setValue(0);
            spnMinuto.getValueFactory().setValue(0);
            spnSegundo.getValueFactory().setValue(0);
            spnCentisegundo.getValueFactory().setValue(0);
        } else if (code == 403) {
            pnMenu.setVisible(false);
            pnLogin.setVisible(true);
            sesionAlert.setHeaderText("Su sesion ha caducado.");
            sesionAlert.setContentText("Vuelva a inicar sesion para continuar.");
            sesionAlert.show();
        }

    }


    /**
     * Método que verifica si se han llenado los campos necesarios para poder iniciar sesion o registrar un usuario y
     * activa o desactiva el boton correspondiente según sea el caso.
     */
    private void verificarLogin() {
        if (txtNUser.getText().length() == 0 || cmbEstado.getValue() == null || cmbGenero.getValue() == null
                || txtEdad.getText().length() == 0 || txtNPass.getText().length() == 0
                || txtNRPass.getText().length() == 0 || !txtNPass.getText().equals(txtNRPass.getText())) {
            btnRegister.setDisable(true);
        } else {
            btnRegister.setDisable(false);
        }
        if (txtUser.getText().length() == 0 || txtPass.getText().length() == 0) {
            btnLogin.setDisable(true);
        } else {
            btnLogin.setDisable(false);
        }
    }


    /**
     * Método que verifica si se han llenado los campos necesarios para poder añadir una carrera al registro y activa o
     * desactiva el boton de registrar según sea el caso.
     */
    private void verificarAnadir() {
        if (cmbTipo.getValue() == null || dtpFecha.getValue() == null || txtDistancia.getText().length() == 0) {
            btnRegistrarCarrera.setDisable(true);
        } else {
            btnRegistrarCarrera.setDisable(false);
        }
    }


    /**
     * Metodo que llama al método sendGetBitacora de la clase cliente con la tipo="P" para obtener una lista de carreras
     * para añadirlas a la tabla de la bitacora personal. En caso de no recibir la lista de carreras se asume que la
     * sesion caduco, se manda el mensaje de alerta correspondiente al usuario y se regresa a la pantalla de inicio de
     * sesion.
     */
    private void llenarBitacoraP() {
        LinkedList<Carrera> lista = client.sendGetBitacora("P", Sesion.GetInstance().getCorredor());
        if (lista != null) {
            tblPersonal.getItems().clear();
            while (!lista.isEmpty())
                tblPersonal.getItems().add(lista.remove());
        } else {
            pnMenu.setVisible(false);
            pnLogin.setVisible(true);
            sesionAlert.setHeaderText("Su sesion ha caducado.");
            sesionAlert.setContentText("Vuelva a inicar sesion para continuar.");
            sesionAlert.show();
        }
    }


    /**
     * Metodo que llama al método sendGetBitacora de la clase cliente con la tipo="G" para obtener una lista de carreras
     * para añadirlas a la tabla de la bitacora general. En caso de no recibir la lista de carreras se asume que la
     * sesion caduco, se manda el mensaje de alerta correspondiente al usuario y se regresa a la pantalla de inicio de
     * sesion.
     */
    private void llenarBitacoraG() {
        LinkedList<Carrera> lista = client.sendGetBitacora("G", null);
        if (lista != null) {
            tblGeneral.getItems().clear();
            while (!lista.isEmpty())
                tblGeneral.getItems().add(lista.remove());
        } else {
            pnMenu.setVisible(false);
            pnLogin.setVisible(true);
            sesionAlert.setHeaderText("Su sesion ha caducado.");
            sesionAlert.setContentText("Vuelva a inicar sesion para continuar.");
            sesionAlert.show();
        }
    }
}
