package com.alex.chatclient;

import javafx.scene.control.TextArea;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class MessagesReceiver extends Thread {
    private final BufferedReader reader;

    private final TextArea output;

    private final MainController mainController;

    private boolean mustWork;

    public void switchOff() {
        mustWork = false;
    }

    @Override
    public void run() {
        try {
            String message = reader.readLine();
            while (mustWork && message != null) {
                String text = output.textProperty().getValue();

                message = new String(message.getBytes(), StandardCharsets.UTF_8);

                if (message.contains(" добавился в группу.")) {
                    String newUser = message.replace(" добавился в группу.", "");
                    newUser = newUser.replace("Сервер::", "");
                    mainController.addUser(newUser);
                } else {
                    String newUser = message.split("::")[0];
                    if (!newUser.equals("Сервер")) {
                        mainController.addUser(newUser);
                    }
                }

                if (message.contains(" покинул чат")) {
                    String leftUser = message.replace(" покинул чат", "");
                    leftUser = leftUser.replace("Сервер::", "");
                    mainController.deleteUser(leftUser);
                }

                output.textProperty().setValue(text + message + "\n");
                output.setScrollTop(Double.MAX_VALUE);

                message = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
