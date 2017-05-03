package com.alex.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    public static void run() throws IOException, InterruptedException {
        Socket socket = new Socket("alexischat.clienddev.ru", 5003);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

        MessagesReceiver messagesReceiver = new MessagesReceiver(in);
        messagesReceiver.setPriority(Thread.MAX_PRIORITY);
        messagesReceiver.start();

        String input;
        while ((input = userIn.readLine()) != null) {

            out.println(input);

            // Exit condition
            if (input.equalsIgnoreCase("exit")) {

                // Inform user
                String message = "You leave the chat";
                System.out.println(message);

                messagesReceiver.switchOff();

                messagesReceiver.join();

                break;
            }
        }

        out.close();
        socket.close();
    }
}
