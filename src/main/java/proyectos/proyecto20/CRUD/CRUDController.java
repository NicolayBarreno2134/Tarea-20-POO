package proyectos.proyecto20.CRUD;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import proyectos.proyecto20.conexion.Conexion;

import java.sql.*;

public class CRUDController {

    @FXML private TextField txtCedula, txtNombre, txtApellido, txtEdad, txtCorreo;
    @FXML private ComboBox<String> cmbEstadoCivil, cmbCategoria;
    @FXML private RadioButton rbMatutina, rbVespertina, rbNocturna;
    @FXML private TextArea txtObservaciones;
    @FXML private TableView<Participante> tablaParticipantes;
    @FXML private TableColumn<Participante, String> colCedula, colNombre, colApellido, colCorreo, colEstadoCivil, colJornada, colCategoria, colObservaciones;
    @FXML private TableColumn<Participante, Integer> colEdad;

    private ToggleGroup grupoJornada;

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Gestión de Participantes");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        cmbEstadoCivil.setItems(FXCollections.observableArrayList("Solitario", "Pareja", "Escuadra", "Equipos"));
        cmbCategoria.setItems(FXCollections.observableArrayList("Street Fighter", "Tekken", "Smash Bros","Mortal Kombat"));

        grupoJornada = new ToggleGroup();
        rbMatutina.setToggleGroup(grupoJornada);
        rbVespertina.setToggleGroup(grupoJornada);
        rbNocturna.setToggleGroup(grupoJornada);

        colCedula.setCellValueFactory(data -> data.getValue().cedulaProperty());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colApellido.setCellValueFactory(data -> data.getValue().apellidoProperty());
        colEdad.setCellValueFactory(data -> data.getValue().edadProperty().asObject());
        colCorreo.setCellValueFactory(data -> data.getValue().correoProperty());
        colEstadoCivil.setCellValueFactory(data -> data.getValue().estadoCivilProperty());
        colJornada.setCellValueFactory(data -> data.getValue().jornadaProperty());
        colCategoria.setCellValueFactory(data -> data.getValue().categoriaProperty());
        colObservaciones.setCellValueFactory(data -> data.getValue().observacionesProperty());

        cargarDatos();
    }

    @FXML
    private void guardar() {
        if (!validarFormulario()) return;
        try (Connection con = Conexion.getInstance()) {
            String sql = "INSERT INTO participantes (cedula, nombre, apellido, edad, correo, estado_civil, jornada, categoria, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtCedula.getText());
            ps.setString(2, txtNombre.getText());
            ps.setString(3, txtApellido.getText());
            ps.setInt(4, Integer.parseInt(txtEdad.getText()));
            ps.setString(5, txtCorreo.getText());
            ps.setString(6, cmbEstadoCivil.getValue());
            ps.setString(7, ((RadioButton) grupoJornada.getSelectedToggle()).getText());
            ps.setString(8, cmbCategoria.getValue());
            ps.setString(9, txtObservaciones.getText());

            ps.executeUpdate();
            mostrarAlerta("Participante guardado correctamente.", Alert.AlertType.INFORMATION);
            cargarDatos();
            limpiar();
        } catch (Exception e) {
            mostrarAlerta("Error al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void actualizar() {
        if (!validarFormulario()) return;
        try (Connection con = Conexion.getInstance()) {
            String sql = "UPDATE participantes SET nombre=?, apellido=?, edad=?, correo=?, estado_civil=?, jornada=?, categoria=?, observaciones=? WHERE cedula=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellido.getText());
            ps.setInt(3, Integer.parseInt(txtEdad.getText()));
            ps.setString(4, txtCorreo.getText());
            ps.setString(5, cmbEstadoCivil.getValue());
            ps.setString(6, ((RadioButton) grupoJornada.getSelectedToggle()).getText());
            ps.setString(7, cmbCategoria.getValue());
            ps.setString(8, txtObservaciones.getText());
            ps.setString(9, txtCedula.getText());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                mostrarAlerta("Participante actualizado correctamente.", Alert.AlertType.INFORMATION);
                cargarDatos();
                limpiar();
            } else {
                mostrarAlerta("No se encontró participante con esa cédula.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al actualizar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void eliminar() {
        try (Connection con = Conexion.getInstance()) {
            String sql = "DELETE FROM participantes WHERE cedula=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtCedula.getText());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                mostrarAlerta("Participante eliminado correctamente.", Alert.AlertType.INFORMATION);
                cargarDatos();
                limpiar();
            } else {
                mostrarAlerta("No se encontró participante con esa cédula.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al eliminar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void limpiar() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtCorreo.clear();
        cmbEstadoCivil.getSelectionModel().clearSelection();
        cmbCategoria.getSelectionModel().clearSelection();
        grupoJornada.selectToggle(null);
        txtObservaciones.clear();
    }

    private void cargarDatos() {
        ObservableList<Participante> lista = FXCollections.observableArrayList();
        try (Connection con = Conexion.getInstance()) {
            String sql = "SELECT * FROM participantes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Participante(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("correo"),
                        rs.getString("estado_civil"),
                        rs.getString("jornada"),
                        rs.getString("categoria"),
                        rs.getString("observaciones")
                ));
            }
            tablaParticipantes.setItems(lista);
        } catch (Exception e) {
            mostrarAlerta("Error al cargar datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private boolean validarFormulario() {
        if (txtCedula.getText().isEmpty() || !txtCedula.getText().matches("\\d+")) {
            mostrarAlerta("La cédula es obligatoria y debe contener solo números.", Alert.AlertType.WARNING);
            return false;
        }

        if (txtNombre.getText().isEmpty()) {
            mostrarAlerta("El nombre es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }

        if (txtApellido.getText().isEmpty()) {
            mostrarAlerta("El apellido es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }

        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText());
            if (edad < 5) {
                mostrarAlerta("La edad debe ser mayor a 5 años.", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("La edad debe ser un número válido.", Alert.AlertType.WARNING);
            return false;
        }

        String correo = txtCorreo.getText();
        if (correo.isEmpty() || !correo.contains("@")) {
            mostrarAlerta("El correo es obligatorio y debe contener '@'.", Alert.AlertType.WARNING);
            return false;
        }

        try (Connection con = Conexion.getInstance()) {
            String sql = "SELECT COUNT(*) FROM participantes WHERE correo=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                mostrarAlerta("El correo ya está registrado.", Alert.AlertType.WARNING);
                return false;
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al validar correo: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

}
