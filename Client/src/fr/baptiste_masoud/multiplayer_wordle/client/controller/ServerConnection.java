package fr.baptiste_masoud.multiplayer_wordle.client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
    private Socket socket;
    private final MessageReader messageReader;
    private final MessageSender messageSender;

    public ServerConnection(String address, int port, Controller Controller) throws IOException {
        socket = new Socket(address, port);
        System.out.println("Connected");

        this.messageReader = new MessageReader(this, new ObjectInputStream(socket.getInputStream()), Controller);
        this.messageReader.start();

        this.messageSender = new MessageSender(this, new ObjectOutputStream(socket.getOutputStream()));
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }
}