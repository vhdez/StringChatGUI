package com.example.stringchatgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReaderThread implements Runnable{
    Socket socket;
    ShoebQueue myData;
    BufferedReader in;

    public ReaderThread(Socket socket, ShoebQueue myData) throws Exception {
        this.socket = socket;
        this.myData = myData;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        while (!Thread.interrupted()) {
            // READ from Socket
            String message = null;
            try {
                message = in.readLine();
            } catch (IOException e) {
                System.out.println("ReaderThread ERROR: " + e);
            }

            // IGNORE null messages by continuing to next while loop iteration
            if (message == null) {
                continue;
            }

            // PUT to myData (to communicate with WriterThread)
            boolean success = myData.put(message);
            while (!success) {
                success = myData.put(message);
            }
            System.out.println("READER THREAD READ: " + message);
        }

        try {
            in.close();
            socket.close();
        } catch (Exception ex) {
            System.out.println("ServerConnectorThread closing failed: " + ex);
        }

    }
}
