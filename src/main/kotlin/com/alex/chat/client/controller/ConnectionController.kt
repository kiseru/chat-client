package com.alex.chat.client.controller

import com.alex.chat.client.AppInitializer
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField

class ConnectionController {

    @FXML
    private lateinit var groupTextField: TextField

    @FXML
    private lateinit var nameTextField: TextField

    @FXML
    private lateinit var connectionButton: Button

    @FXML
    private lateinit var exitButton: Button

    @FXML
    fun initialize() {
        connectionButton.setOnMouseClicked { onConnectionButtonClick() }
        exitButton.setOnMouseClicked { AppInitializer.primaryStage.close() }
    }

    private fun onConnectionButtonClick() {
        val name = nameTextField.text
        val group = groupTextField.text
        if (name == "" || group == "") {
            return
        }

        MainController.setNameAndGroup(name, group)
        val view = "/views/main_form.fxml"
        val loader = FXMLLoader()
        val page = loader.load<Parent>(javaClass.getResourceAsStream(view))
        val scene = Scene(page)
        AppInitializer.primaryStage.close()
        AppInitializer.primaryStage.scene = scene
        AppInitializer.primaryStage.show()
    }
}