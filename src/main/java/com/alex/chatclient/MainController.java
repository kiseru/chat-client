package com.alex.chatclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Label name;
    public Button sendButton;

    private static PrintWriter out;
    private static boolean isConnected;
    private static String nameAndGroup;

    static void setIsConnected() {
        MainController.isConnected = true;
    }

    @FXML
    void initialize() {

        AppInitializer.receiver = new MessagesReceiver(AppInitializer.reader, outputTextArea);
        AppInitializer.receiver.setDaemon(true);
        AppInitializer.receiver.start();

        name.setText(nameAndGroup);

        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
    }

    // Действия по нажатию кнопки "Отправить"
    public void sendAction(MouseEvent mouseEvent) {
        this.sendMessage();
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

        // Нельзя отправлять пустые сообщения
        if (message.equals("")) return;

        // Отправляем сообщение серверу
        AppInitializer.writer.println(message);

        if (message.equalsIgnoreCase("disconnect exit car movie guards")) {
            disconnect();
        }
    }

    public static void setNameAndGroup(String name, String group) {

        MainController.nameAndGroup = String.format(
                "%s | %s",
                group,
                name
        );
    }
}
