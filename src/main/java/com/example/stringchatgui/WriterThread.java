package com.example.stringchatgui;

import javafx.application.Platform;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class WriterThread implements Runnable {
    ShoebQueue myData;
    boolean isServer;
    Socket socket;
    ArrayList<Socket> sockets;
    MessageController myController;

    public WriterThread(Socket socket, ShoebQueue myData, MessageController myController) throws Exception {
        this.socket = socket;
        this.myData = myData;
        this.isServer = false;
        this.myController = myController;
    }

    public WriterThread(ArrayList<Socket> sockets, ShoebQueue myData, MessageController myController) throws Exception {
        this.sockets = sockets;
        this.myData = myData;
        this.isServer = true;
        this.myController = myController;
    }

    public void run() {
        while (!Thread.interrupted()) {
            // GET from myData (to communicate from ReaderThread)
            Object message = myData.get();
            while (message == null) {
                message = myData.get();
            }
            // PROCESS the message:
            if (isServer) {
                // Servers WRITE the message to ALL Clients
                System.out.println("SERVER WROTE: LOL SOMEONE wrote: " + message);
                for (Socket eachSocket: sockets) {
                    try {
                        PrintWriter out = new PrintWriter(eachSocket.getOutputStream(), true);
                        out.println("LOL SOMEONE wrote: " + message);
                    } catch (Exception ex) {
                        System.out.println("SERVER failed to write to socket " + eachSocket);
                    }
                }
            }

            // BOTH Server and Client display their message in their View
            Object finalMessage = message;
            Platform.runLater(new Runnable() {
                public void run() {
                    myController.receiveMessage(finalMessage.toString());
                }
            });
        }

        try {
            socket.close();
        } catch (Exception ex) {
            System.out.println("ServerConnectorThread closing failed: " + ex);
        }
    }
}
