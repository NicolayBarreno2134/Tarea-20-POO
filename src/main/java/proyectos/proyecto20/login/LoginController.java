package proyectos.proyecto20.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    @FXML

    private void ingresar() {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Por favor, completa todos los campos.", Alert.AlertType.WARNING);
        } else if (usuario.equals("JugadorPRO") && contrasena.equals("1234")) {
            mostrarAlerta("Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);
            abrirVentanaCrud();
        } else {
            mostrarAlerta("Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void salir() {
        System.exit(0);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Login del Concurso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void abrirVentanaCrud() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyectos/proyecto20/crud.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestión de Participantes");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) txtUsuario.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            mostrarAlerta("Error al abrir la ventana CRUD: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
