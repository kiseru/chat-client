package com.alex.chatclient;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;

public class MessagesReceiver extends Thread {

    private boolean mustWork;

    private BufferedReader reader;
    private TextArea output;

    public MessagesReceiver(BufferedReader reader, TextArea output) {
        this.reader = reader;
        this.output = output;
        this.mustWork = true;
    }

    // Говорим потоку, что он больше не нужен и должен отключиться
    public void switchOff() {
        mustWork = false;
    }

    @Override
    public void run() {

        String message;
        try {
            while (mustWork && (message = reader.readLine()) != null) {
                String text = output.textProperty().getValue();

                message = new String(message.getBytes(), "UTF-8");

                output.textProperty().setValue(text + message + "\n");

                // Прокручиваем сообщения до конца вниз
                output.setScrollTop(Double.MAX_VALUE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
