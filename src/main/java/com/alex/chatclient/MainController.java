package com.alex.chatclient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainController {
    private static boolean isConnected;

    private static String group;

    private static String name;

    private final Set<String> users = new HashSet<>();

    private PrintWriter writer;

    private MessagesReceiver receiver;

    public ListView<String> usersList;

    public TextField inputTextField;

    public TextArea outputTextArea;

    public Label groupAndNameTitle;

    public Button sendButton;

    @FXML
    void initialize() throws IOException {
        isConnected = true;
        String hostName = "localhost";
        Socket socket = new Socket(hostName, 5003);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer = new PrintWriter(socket.getOutputStream(), true);

        writer.println(name);
        writer.println(group);

        receiver = new MessagesReceiver(reader, outputTextArea, this);
        receiver.setDaemon(true);
        receiver.start();
        groupAndNameTitle.setText(String.format("%s | %s", group, name));
        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });

        AppInitializer.primaryStage.setOnCloseRequest(event -> disconnect());
    }

    public void sendAction(MouseEvent mouseEvent) {
        this.sendMessage();
    }

    public void disconnect() {

        try {
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
        String message = inputTextField.getText();
        inputTextField.clear();
        if (message.equals("")) {
            return;
        }

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
        if (users.contains(name)) {
            return;
        }
        users.add(name);
        usersList.setItems(FXCollections.observableList(new ArrayList<>(users)));
    }

    public void deleteUser(String leftUser) {
        users.remove(leftUser);
        usersList.setItems(FXCollections.observableList(new ArrayList<>(users)));
    }
}
