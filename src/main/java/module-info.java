module com.cajero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens com.cajero to javafx.fxml;

    exports com.cajero;
}
