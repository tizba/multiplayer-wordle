package fr.baptiste_masoud.multiplayer_wordle.client.controller;

import fr.baptiste_masoud.multiplayer_wordle.messages.Message;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.ClientToServerMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.ServerToClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReader extends Thread {
    private final ObjectInputStream objectInputStream;
    private final ServerConnection serverConnection;
    private Controller controller;

    public MessageReader(ServerConnection serverConnection, ObjectInputStream objectInputStream, Controller controller) {
        this.serverConnection = serverConnection;
        this.objectInputStream = objectInputStream;
        this.controller = controller;
    }


    @Override
    public void run() {
        while (this.isAlive()) {
            try {
                ServerToClientMessage message = (ServerToClientMessage) objectInputStream.readObject();
                handleMessage(message);
            } catch (IOException e) {
                controller.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);
                controller.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
                controller.setServerConnection(null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(ServerToClientMessage message) {
        System.out.println("Handling new message: " + message.getMessageType());
        switch (message.getMessageType()) {
            case TOO_MANY_PLAYERS -> {
                this.handleTooManyPlayers();
            }
            case SUCCESSFUL_CONNECTION -> {
                this.handleSuccessfulConnection();
            }
            case SUCCESSFUL_DISCONNECTION -> {
                this.handleSuccessfulDisconnection();
            }
        }
    }

    private void handleTooManyPlayers() {
        controller.setServerConnection(null);
        controller.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
        controller.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);
    }

    private void handleSuccessfulDisconnection() {
        controller.setServerConnection(null);
        controller.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);
        controller.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
    }

    private void handleSuccessfulConnection() {
        controller.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(false);
        controller.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(true);
    }
}
