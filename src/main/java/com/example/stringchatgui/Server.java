package com.example.stringchatgui;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello I'm the server...");
        ServerConnectorThread connectorThread = new ServerConnectorThread(25783, null);
        Thread thread1 = new Thread(connectorThread);
        thread1.start();
    }
}