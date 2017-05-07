package com.alex.chatclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField groupTextField;

    @FXML
    private Button connectButton;

    public static TextArea output;
    public static Stage dialogStage;

    @FXML
    private void initialize() {}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void connectHandle(MouseEvent mouseEvent) throws IOException {
        String name = nameTextField.getText();
        String group = groupTextField.getText();

        Socket socket = new Socket("localhost", 5003);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        AppInitializer.receiver = new MessagesReceiver(reader, output);
        AppInitializer.receiver.start();

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(name);
        writer.println(group);

        MainController.out = writer;

        dialogStage.close();
    }
}
