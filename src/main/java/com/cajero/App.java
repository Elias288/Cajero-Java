package com.cajero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.cajero.manager.SessionManager;
import com.cajero.modelo.Usuario;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    Usuario usuario = SessionManager.getInstance().getUsuarioActual();

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        String page = usuario != null ? "main" : "primary";
        scene = new Scene(loadFXML(page));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        // scene.setRoot(loadFXML(fxml));

        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        ((Stage) scene.getWindow()).sizeToScene();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}