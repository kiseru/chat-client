package com.alex.chatclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MainController {

    public static PrintWriter out;

    public TextField inputTextField;
    public TextArea outputTextArea;
    public Button disconnectionButton;
    public Button connectionButton;
    public Button sendButton;

    private Stage dialogStage;
    private MainForm mainForm;
    private static boolean isConnected;

    public MainController() {
        sendButton.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendAction();
            }
        });

        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendAction();
            }
        });
    }

    public static void setIsConnected() {
        MainController.isConnected = true;
    }

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

        ConnectionController.setMainController(this);

        connectionDialogStage.showAndWait();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void sendAction() {
        String message = inputTextField.getText();
        inputTextField.clear();

        out.println(message);

        if (message.equalsIgnoreCase("disconnect exit car movie guards")) {
            disconnect();
        }
    }

    public void disconnectAction(MouseEvent mouseEvent) {
        disconnect();

        connectionButton.setDisable(false);
        disconnectionButton.setDisable(true);
        sendButton.setDisable(true);
    }

    public static void disconnect() {

        // Если есть соединение, то выходим из группы
        if (isConnected) {
            try {
                out.println("disconnect exit car movie guards");
                AppInitializer.receiver.switchOff();
                AppInitializer.receiver.join();
                isConnected = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pressedEnterHandler(KeyEvent keyEvent) throws UnsupportedEncodingException, InterruptedException {
        if (keyEvent.getCode() == KeyCode.ENTER && !sendButton.isDisable()) {
            sendAction();
        }
    }
}
