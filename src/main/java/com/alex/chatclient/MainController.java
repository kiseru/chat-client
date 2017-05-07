package com.alex.chatclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

public class MainController {

    public TextField inputTextField;
    public TextArea outputTextArea;

    public static PrintWriter out;

    private Stage dialogStage;
    private MainForm mainForm;

    public void connectAction(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainForm.class.getResource("/fxml/connection_form.fxml"));
        Pane page = loader.load();

        Stage connectionDialogStage = new Stage();
        connectionDialogStage.setTitle("Подключение");
        connectionDialogStage.initModality(Modality.WINDOW_MODAL);
        connectionDialogStage.initOwner(AppInitializer.primaryStage);

        Scene scene = new Scene(page);
        connectionDialogStage.setScene(scene);

        ConnectionController.output = outputTextArea;
        ConnectionController.dialogStage = connectionDialogStage;

        connectionDialogStage.showAndWait();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void sendAction(MouseEvent mouseEvent) throws InterruptedException {
        String message = inputTextField.getText();
        inputTextField.clear();

        out.println(message);

        if (message.equalsIgnoreCase("disconnect exit car movie guards")) {
            disconnect();
        }
    }

    public void disconnectAction(MouseEvent mouseEvent) throws InterruptedException {
        disconnect();
    }

    public static void disconnect() throws InterruptedException {
        out.println("disconnect exit car movie guards");
        AppInitializer.receiver.switchOff();
        AppInitializer.receiver.join();
        AppInitializer.primaryStage.close();
    }
}
