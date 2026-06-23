package proyectos.proyecto20.login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    @FXML
    private void ingresar() {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Por favor, completa todos los campos.", Alert.AlertType.WARNING);
        } else if (usuario.equals("admin") && contrasena.equals("1234")) {
            mostrarAlerta("Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);
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
}
