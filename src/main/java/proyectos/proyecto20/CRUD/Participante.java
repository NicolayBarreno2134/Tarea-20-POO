package proyectos.proyecto20.CRUD;

import javafx.beans.property.*;

public class Participante {
    private final StringProperty cedula;
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final IntegerProperty edad;
    private final StringProperty correo;
    private final StringProperty estadoCivil;
    private final StringProperty jornada;
    private final StringProperty categoria;
    private final StringProperty observaciones;

    public Participante(String cedula, String nombre, String apellido, int edad, String correo,
                        String estadoCivil, String jornada, String categoria, String observaciones) {
        this.cedula = new SimpleStringProperty(cedula);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.edad = new SimpleIntegerProperty(edad);
        this.correo = new SimpleStringProperty(correo);
        this.estadoCivil = new SimpleStringProperty(estadoCivil);
        this.jornada = new SimpleStringProperty(jornada);
        this.categoria = new SimpleStringProperty(categoria);
        this.observaciones = new SimpleStringProperty(observaciones);
    }

    public StringProperty cedulaProperty() { return cedula; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty apellidoProperty() { return apellido; }
    public IntegerProperty edadProperty() { return edad; }
    public StringProperty correoProperty() { return correo; }
    public StringProperty estadoCivilProperty() { return estadoCivil; }
    public StringProperty jornadaProperty() { return jornada; }
    public StringProperty categoriaProperty() { return categoria; }
    public StringProperty observacionesProperty() { return observaciones; }
}
