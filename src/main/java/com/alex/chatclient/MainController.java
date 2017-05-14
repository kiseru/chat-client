package com.alex.chatclient;

import javafx.fxml.FXML;
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

public class MainController {

    public TextField inputTextField;
    public TextArea outputTextArea;
    public Button disconnectionButton;
    public Button connectionButton;
    public Button sendButton;

    public static PrintWriter out;
    private static boolean isConnected;

    public static void setIsConnected() {
        MainController.isConnected = true;
    }

    @FXML
    void initialize() {

        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && isConnected) {
                sendMessage();
            }
        });
    }

    // Действия по нажатию кнопки "Подключиться"
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

    // Действия по нажатию кнопки "Отправить"
    public void sendAction(MouseEvent mouseEvent) {
        this.sendMessage();
    }

    // Действия по нажатию кнопки "Откличиться"
    public void disconnectAction(MouseEvent mouseEvent) {
        disconnect();

        connectionButton.setDisable(false);
        disconnectionButton.setDisable(true);
        sendButton.setDisable(true);
    }

    public static void disconnect() {

        try {
            // Если есть соединение, то выходим из группы
            if (isConnected) {
                out.println("disconnect exit car movie guards");
                AppInitializer.receiver.switchOff();
                AppInitializer.receiver.join();
                isConnected = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {

        // Получаем сообщение из поля ввода
        String message = inputTextField.getText();

        // Отчищаем поля ввода
        inputTextField.clear();

        // Отправляем сообщение серверу
        out.println(message);

        if (message.equalsIgnoreCase("disconnect exit car movie guards")) {
            disconnect();
        }
    }
}
