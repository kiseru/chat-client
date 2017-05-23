package com.alex.chatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.Charset;

public class AppInitializer extends Application {

    public static boolean isTest;
    public static Stage primaryStage;
    public static MessagesReceiver receiver;
    public final static String charset = Charset.defaultCharset().toString();
    public final static String BAD_CHARSET = "windows-1251";

    public static void main(String[] args) throws IOException, InterruptedException {

        if (charset.equals(BAD_CHARSET)) {
            System.out.println("You must use right encoding! Bye!");
            return;
        }

        isTest = args.length != 0 && args[0].equals("-t");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        String fxmlFile = "/views/main_form.fxml";

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(root);

        primaryStage.setOnCloseRequest(event -> {
            MainController.disconnect();
        });

        primaryStage.setTitle("Alexis Chat");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
