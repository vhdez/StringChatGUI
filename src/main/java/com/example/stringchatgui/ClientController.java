package com.example.stringchatgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientController implements MessageController {
    public TextField ipText;
    public TextField portText;
    public Button connectButton;
    public TextField messageText;
    public Button sendButton;
    public ListView<String> messageList;

    ShoebQueue messagesIn;
    PrintWriter socketOut;

    boolean connectedToServer;

    public void initialize() {
        connectedToServer = false;
        messagesIn = new ShoebQueue();
    }

    public void connect() throws Exception {

        if (!connectedToServer) {
            String ipAddress = ipText.getText();
            int portNumber = Integer.parseInt(portText.getText());
            Socket socket = new Socket(ipAddress, portNumber);
            ReaderThread read1 = new ReaderThread(socket, messagesIn);
            Thread reader = new Thread(read1);
            WriterThread write1 = new WriterThread(socket, messagesIn, this);
            Thread writer = new Thread(write1);
            reader.start();
            writer.start();
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            connectedToServer = true;
            connectButton.setText("Disconnect");
            sendButton.setDisable(false);
        } else {
            connectedToServer = false;
            connectButton.setText("Connect");
            sendButton.setDisable(true);
        }
    }

    public void send() {
        String message = messageText.getText();
        socketOut.println(message);
    }

    public void receiveMessage(String message) {
        messageList.getItems().addFirst(message);
    }
}