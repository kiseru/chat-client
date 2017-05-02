package com.alex.chatclient;

import java.io.BufferedReader;
import java.io.IOException;

public class MessagesReceiver extends Thread {

    private BufferedReader reader;

    public MessagesReceiver(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        while (true) {

            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
