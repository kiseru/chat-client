package com.alex.chatclient;

import java.io.BufferedReader;
import java.io.IOException;

public class MessagesReceiver extends Thread {

    private boolean mustWork;

    private BufferedReader reader;

    public MessagesReceiver(BufferedReader reader) {
        this.reader = reader;
        this.mustWork = true;
    }

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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
