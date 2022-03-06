package fr.baptiste_masoud.multiplayer_wordle.client.controller;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.ServerToClientMessage;

import javax.swing.*;
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
                onServerShutdown();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void onServerShutdown() {
        controller.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);
        controller.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
        controller.getGui().getContentPane().setVisible(false);
        controller.setServerConnection(null);
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
            case OPPONENT_NAME -> {
                this.handleNames((OpponentNameMessage) message);
            }
            case GAME_STATE -> {
                this.handleGameState((GameStateMessage) message);
            }
        }
    }

    private void handleTooManyPlayers() {
        controller.setServerConnection(null);
        controller.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
        controller.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);

        // show message
        JPanel panel = new JPanel();
        panel.add(new JLabel("2 players are already playing… try again later"));
        JOptionPane.showConfirmDialog(null, panel, "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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

    private void handleNames(OpponentNameMessage opponentNameMessage) {
        controller.getGui().getWordlePanel().getOpponentPanel().getNameTextField().setText(opponentNameMessage.getName());
    }

    private void handleGameState(GameStateMessage gameStateMessage) {
        controller.getGui().updateWithGameState(gameStateMessage.getGameState());
    }
}
