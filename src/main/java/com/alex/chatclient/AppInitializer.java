package com.alex.chatclient;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class AppInitializer extends Application {

    public static Stage primaryStage;
    public static MessagesReceiver receiver;

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        String fxmlFile = "/fxml/main_form.fxml";

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(root);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    MainController.disconnect();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
