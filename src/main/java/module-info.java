module com.cajero {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cajero to javafx.fxml;
    exports com.cajero;
}
