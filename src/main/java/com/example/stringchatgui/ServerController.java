package com.example.stringchatgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.PrintWriter;
import java.net.Socket;

public class ServerController implements MessageController {
    public Label ipText;
    public TextField portText;
    public Button startButton;
    public ListView<String> messageList;

    public void start() throws Exception {
        int portNumber = Integer.parseInt(portText.getText());
        ServerConnectorThread connectorThread = new ServerConnectorThread(portNumber, this);
        Thread thread1 = new Thread(connectorThread);
        thread1.start();
    }

    public void receiveMessage(String message) {
        messageList.getItems().addFirst(message);
    }

}