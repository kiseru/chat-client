package com.alex.chatclient;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainForm extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AppInitializer.primaryStage = primaryStage;

        String fxmlFile = "/fxml/main_form.fxml";

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        primaryStage.setHeight(400);
        primaryStage.setWidth(605);

        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(Event::consume);

        primaryStage.setTitle("Alexis Chat");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
