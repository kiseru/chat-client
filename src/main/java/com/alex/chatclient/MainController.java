package com.alex.chatclient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class MainController {

    private static boolean isConnected;
    private static String group;
    private static String name;

    private LinkedList<String> users = new LinkedList<>();
    private PrintWriter writer;
    private MessagesReceiver receiver;

    public ListView usersList;
    public TextField inputTextField;
    public TextArea outputTextArea;
    public Label groupAndNameTitle;
    public Button sendButton;

    @FXML
    void initialize() throws IOException {

        isConnected = true;

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
        receiver = new MessagesReceiver(reader, outputTextArea, this);
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

        // Отключение от сервера при нажатии кнопки и переход к окну подключения
        AppInitializer.primaryStage.setOnCloseRequest(event -> disconnect());
    }

    // Действия по нажатию кнопки "Отправить"
    public void sendAction(MouseEvent mouseEvent) {
        this.sendMessage();
    }

    public void disconnect() {

        try {
            // Если есть соединение, то выходим из группы
            if (isConnected) {
                receiver.switchOff();
                writer.println("disconnect exit car movie guards");
                receiver.join();
                isConnected = false;
            }

            AppInitializer.primaryStage.close();

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

    public void addUser(String name) {
        if (users.contains(name)) return;
        users.add(name);
        usersList.setItems(FXCollections.observableList(users));
    }

    public void deleteUser(String leftUser) {
        users.remove(leftUser);
        usersList.setItems(FXCollections.observableList(users));
    }
}
