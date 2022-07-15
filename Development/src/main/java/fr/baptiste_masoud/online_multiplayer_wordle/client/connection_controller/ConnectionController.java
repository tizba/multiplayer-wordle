package fr.baptiste_masoud.online_multiplayer_wordle.client.connection_controller;

import fr.baptiste_masoud.online_multiplayer_wordle.client.gui.GUI;
import fr.baptiste_masoud.online_multiplayer_wordle.messages.c_to_s.ClientToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionController {
    private final GUI gui;
    private boolean connected = false;
    private Socket socket;
    private MessageSender messageSender;

    public ConnectionController(GUI gui) {
        this.gui = gui;
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        connected = true;

        MessageReader messageReader = new MessageReader(this, new ObjectInputStream(this.socket.getInputStream()));
        messageReader.start();

        this.messageSender = new MessageSender(new ObjectOutputStream(this.socket.getOutputStream()));
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
