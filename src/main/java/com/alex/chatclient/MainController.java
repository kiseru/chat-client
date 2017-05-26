package com.alex.chatclient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainController {

    private static boolean isConnected;
    private static String group;
    private static String name;

    private PrintWriter writer;
    private MessagesReceiver receiver;

    public TextField inputTextField;
    public TextArea outputTextArea;
    public Label groupAndNameTitle;
    public Button sendButton;

    static void setIsConnected() {
        MainController.isConnected = true;
    }

    @FXML
    void initialize() throws IOException {

        // В зависимости от того, тестовая версия или нет, выбираем хост. Подключаемся к нему
        String hostName = AppInitializer.isTest ? "localhost" : "alexischat.clienddev.ru";
        Socket socket = new Socket(hostName, 5003);

        // Поток для чтения сообщений с сервера
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Создаем поток записи для отправки на сервер сообщений
        writer = new PrintWriter(socket.getOutputStream(), true);

        // Отправляем своё имя и  номер группы
        writer.println(name);
        writer.println(group);

        // Подключаем поток для получения сообщений
        receiver = new MessagesReceiver(reader, outputTextArea);
        receiver.setDaemon(true);
        receiver.start();

        // Устанавливаем сверху группу и имя
        groupAndNameTitle.setText(
                String.format("%s | %s", group, name)
        );

        // Добавляем обработчик нажатия Enter для отправки сообщений
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

    public void disconnect() {

        try {
            // Если есть соединение, то выходим из группы
            if (isConnected) {
                writer.println("disconnect exit car movie guards");
                receiver.switchOff();
                receiver.join();
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
        writer.println(message);

        if (message.equalsIgnoreCase("disconnect exit car movie guards")) {
            disconnect();
        }
    }

    public static void setNameAndGroup(String name, String group) {

        MainController.name = name;
        MainController.group = group;
    }
}
