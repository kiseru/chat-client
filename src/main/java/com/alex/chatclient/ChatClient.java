package com.alex.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    public static void run(AppInitializer form) throws IOException, InterruptedException {
//        Socket socket = new Socket("alexischat.clienddev.ru", 5003);
        Socket socket = new Socket("localhost", 5003);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Your login: ");
        String login = userIn.readLine();
        out.println(login);

        System.out.println("Your group: ");
        String group = userIn.readLine();
        out.println(group);

//        MessagesReceiver messagesReceiver = new MessagesReceiver(in, form);
//        messagesReceiver.setPriority(Thread.MAX_PRIORITY);
//        messagesReceiver.start();

        String input;
        while ((input = userIn.readLine()) != null) {

            out.println(input);

            // Exit condition
            if (input.equalsIgnoreCase("exit")) {

                // Отключаем получатель сообщений и ждем завершения его работы
//                messagesReceiver.switchOff();
//                messagesReceiver.join();

                break;
            }
        }

        out.close();
        socket.close();
    }
}
