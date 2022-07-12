package com.alex.chat.client.controller;

import com.alex.chat.client.AppInitializer;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConnectionController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField groupTextField;

    @FXML
    private Button connectionButton;

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        connectionButton.setOnMouseClicked(event -> onConnectionButtonClick());
        exitButton.setOnMouseClicked(event -> AppInitializer.primaryStage.close());
    }

    private void onConnectionButtonClick() {
        try {
            String name = nameTextField.getText();
            String group = groupTextField.getText();
            if (name.equals("") || group.equals("")) {
                return;
            }

            MainController.setNameAndGroup(name, group);

            String view = "/views/main_form.fxml";

            FXMLLoader loader = new FXMLLoader();
            Parent page = loader.load(getClass().getResourceAsStream(view));
            Scene scene = new Scene(page);

            AppInitializer.primaryStage.close();

            AppInitializer.primaryStage.setScene(scene);
            AppInitializer.primaryStage.show();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
