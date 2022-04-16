package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulConnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.game.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class Player {
    private String name = "Name";
    private boolean connected;
    private final Game game;
    private final MessageSender messageSender;
    private final MessageReader messageReader;
    private final UUID uuid;


    public Player(Game game, Socket socket) throws IOException {
        this.game = game;
        this.messageSender = new MessageSender(new ObjectOutputStream(socket.getOutputStream()));
        this.messageReader = new MessageReader(new ObjectInputStream(socket.getInputStream()), this);
        this.uuid = UUID.randomUUID();
    }

    public void disconnect() {
        messageSender.sendMessage(new SuccessfulDisconnectionMessage());
        this.connected = false;
    }

    public void acceptConnection() {
        this.connected = true;
        this.messageReader.start();
        this.messageSender.sendMessage(new SuccessfulConnectionMessage());
    }

    public boolean isConnected() {
        return connected;
    }

    public Game getGame() {
        return game;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setName(String name) {
        this.name = name;
    }
}
