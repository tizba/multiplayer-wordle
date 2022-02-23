package fr.baptiste_masoud.multiplayer_wordle.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    final MessageReader messageReader;

    public Client(String address, int port) throws IOException {
            socket = new Socket(address, port);
            System.out.println("Connected");
            this.messageReader = new MessageReader(this, new ObjectInputStream(socket.getInputStream()));
            this.messageReader.start();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("127.0.0.1", 5000);
    }
}