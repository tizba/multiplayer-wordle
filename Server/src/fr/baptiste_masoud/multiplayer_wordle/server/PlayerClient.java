package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.ClientToServerMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulConnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;

import java.io.*;
import java.net.Socket;


class PlayerClient extends Thread {

    private final Socket socket;
    private final MessageSender messageSender;
    private final ObjectInputStream objectInputStream;
    private boolean connected = true;
    private Game game;

    public PlayerClient(Socket socket) throws IOException {
        this.socket = socket;
        this.messageSender = new MessageSender(new ObjectOutputStream(socket.getOutputStream()));
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    @Override
    public void run() {
        messageSender.sendMessage(new SuccessfulConnectionMessage());
        while (connected) {
            try {
                ClientToServerMessage message = (ClientToServerMessage) objectInputStream.readObject();
                handleMessage(message);
            } catch (IOException e) {
                this.connected = false;
                game.getServer().newGame();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(ClientToServerMessage message) {
        switch (message.getMessageType()) {
            case DISCONNECT -> {
                game.getServer().newGame();
            }
        }
    }

}
