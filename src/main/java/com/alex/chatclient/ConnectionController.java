package com.alex.chatclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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

    static TextArea output;
    static Stage dialogStage;

    private static MainController mainController;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void connectHandle(MouseEvent mouseEvent) throws IOException {

        // Получаем имя и название группы для подключения
        String name = nameTextField.getText();
        String group = groupTextField.getText();

        MainController.setNameAndGroup(name, group);

        // Если поля пустые, то ничего не делаем
        if (name.equals("") || group.equals("")) {
            return;
        }

        // В зависимости от того, тестовая версия или нет, выбираем хост. Подключаемся к нему
        String hostName = AppInitializer.isTest ? "localhost" : "alexischat.clienddev.ru";
        Socket socket = new Socket(hostName, 5003);

        // Поток для чтения сообщений с сервера
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Создаем поток записи для отправки на сервер сообщений
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        // Отправляем своё имя и  номер группы
        writer.println(name);
        writer.println(group);

        AppInitializer.writer = writer;
        AppInitializer.reader = reader;

        String view = "/views/main_form.fxml";

        FXMLLoader loader = new FXMLLoader();
        Parent page = loader.load(getClass().getResourceAsStream(view));

        Scene scene = new Scene(page);

        AppInitializer.primaryStage.close();

        AppInitializer.primaryStage.setScene(scene);
        AppInitializer.primaryStage.show();
    }



    static void setMainController(MainController mainController) {
        ConnectionController.mainController = mainController;
    }

    public void exitHandle(MouseEvent mouseEvent) {
        AppInitializer.primaryStage.close();
    }
}
