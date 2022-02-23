package fr.baptiste_masoud;

import java.net.*;
import java.io.*;

public class Client {
    private Socket socket = null;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public Client(String address, int port) throws IOException {
            socket = new Socket(address, port);
            System.out.println("Connected");
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            while (true){}
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("127.0.0.1", 5000);
    }
}