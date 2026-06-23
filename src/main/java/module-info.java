module proyectos.proyecto20 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;


    opens proyectos.proyecto20 to javafx.fxml;
    exports proyectos.proyecto20;
}