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
        while (mustWork) {

            String message;
            try {
                if ((message = reader.readLine()) != null) {
                    System.out.println(message);

                    String text = output.getText();

                    output.setText(text + message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
