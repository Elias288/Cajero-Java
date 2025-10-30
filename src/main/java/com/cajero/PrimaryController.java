package com.cajero;

import java.io.IOException;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchToCreateUser() throws IOException {
        App.setRoot("createUser");
    }
}
