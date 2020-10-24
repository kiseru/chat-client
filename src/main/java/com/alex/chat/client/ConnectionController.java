package com.alex.chat.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ConnectionController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField groupTextField;

    @FXML
    private void initialize() {
    }

    public void connectHandle(MouseEvent mouseEvent) throws IOException {
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
    }

    public void exitHandle(MouseEvent mouseEvent) {
        AppInitializer.primaryStage.close();
    }
}
