module com.cajero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.cajero to javafx.fxml;

    exports com.cajero;
}
