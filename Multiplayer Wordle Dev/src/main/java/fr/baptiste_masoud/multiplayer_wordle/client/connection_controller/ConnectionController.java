package fr.baptiste_masoud.multiplayer_wordle.client.connection_controller;

import fr.baptiste_masoud.multiplayer_wordle.client.gui.GUI;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.ClientToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionController {
    private boolean connected = false;
    private Socket socket;
    private MessageReader messageReader;
    private MessageSender messageSender;
    private final GUI gui;

    public ConnectionController(GUI gui) {
        this.gui = gui;
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        connected = true;

        this.messageReader = new MessageReader(this, new ObjectInputStream(this.socket.getInputStream()));
        this.messageReader.start();

        this.messageSender = new MessageSender(this, new ObjectOutputStream(this.socket.getOutputStream()));
    }

    public void sendMessage(ClientToServerMessage message) {
        this.messageSender.sendMessage(message);
    }

    public GUI getGui() {
        return gui;
    }

    public boolean isConnected() {
        return connected;
    }

    public void disconnect() {
        this.connected = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
