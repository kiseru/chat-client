package com.alex.chatclient;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;

public class MessagesReceiver extends Thread {

    private boolean mustWork;

    private BufferedReader reader;
    private TextArea output;
    private MainController mainController;

    public MessagesReceiver(BufferedReader reader, TextArea output, MainController mainController) {
        this.reader = reader;
        this.output = output;
        this.mustWork = true;
        this.mainController = mainController;
    }

    // Говорим потоку, что он больше не нужен и должен отключиться
    public void switchOff() {
        mustWork = false;
    }

    @Override
    public void run() {

        try {
            String message = reader.readLine();
            while (mustWork && message != null) {
                String text = output.textProperty().getValue();

                message = new String(message.getBytes(), "UTF-8");

                if (message.contains(" добавился в группу.")) {
                    String newUser = message.replace(" добавился в группу.", "");
                    newUser = newUser.replace("Сервер::", "");
                    mainController.addUser(newUser);

                }

                output.textProperty().setValue(text + message + "\n");

                // Прокручиваем сообщения до конца вниз
                output.setScrollTop(Double.MAX_VALUE);

                message = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
