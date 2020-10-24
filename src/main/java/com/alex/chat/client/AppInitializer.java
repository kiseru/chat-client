package com.alex.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppInitializer extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppInitializer.primaryStage = primaryStage;

        String fxmlFile = "/views/connection_form.fxml";

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(root);

        primaryStage.setOnCloseRequest(event -> primaryStage.close());

        primaryStage.setTitle("Alexis Chat");
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("/stylesheets/main.css");

        primaryStage.show();
    }
}