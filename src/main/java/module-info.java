module proyectos.proyecto20 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens proyectos.proyecto20 to javafx.fxml;
    exports proyectos.proyecto20;

    exports proyectos.proyecto20.login;
    opens proyectos.proyecto20.login to javafx.fxml;

    exports proyectos.proyecto20.CRUD;
    opens proyectos.proyecto20.CRUD to javafx.fxml;
}
