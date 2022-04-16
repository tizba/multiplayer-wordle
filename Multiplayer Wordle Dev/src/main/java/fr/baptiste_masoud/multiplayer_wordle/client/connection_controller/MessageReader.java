package fr.baptiste_masoud.multiplayer_wordle.client.connection_controller;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.ServerToClientMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SubmissionErrorMessage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReader extends Thread {
    private final ObjectInputStream objectInputStream;
    private final ConnectionController connectionController;

    public MessageReader(ConnectionController connectionController, ObjectInputStream objectInputStream) {
        this.connectionController = connectionController;
        this.objectInputStream = objectInputStream;
    }


    @Override
    public void run() {
        while (connectionController.isConnected()) {
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
        connectionController.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);
        connectionController.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
        connectionController.getGui().getContentPane().setVisible(false);
        connectionController.disconnect();
    }

    private void handleMessage(ServerToClientMessage message) {
        System.out.println("Handling new message: " + message.getMessageType());
        switch (message.getMessageType()) {
            case TOO_MANY_PLAYERS -> this.handleTooManyPlayers();
            case SUCCESSFUL_CONNECTION -> this.handleSuccessfulConnection();
            case SUCCESSFUL_DISCONNECTION -> this.handleSuccessfulDisconnection();
            case OPPONENT_NAME -> this.handleNames((OpponentNameMessage) message);
            case GAME_STATE_DATA -> this.handleGameState((GameStateMessage) message);
            case SUBMISSION_ERROR -> this.handleSubmissionError((SubmissionErrorMessage) message);
        }
    }

    private void handleSubmissionError(SubmissionErrorMessage message) {
        connectionController.getGui().getWordlePanel().getPlayerPanel().setSubmissionErrorLabelText(message.getError());
    }

    private void handleTooManyPlayers() {
        connectionController.disconnect();
        connectionController.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
        connectionController.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);

        // show message
        JPanel panel = new JPanel();
        panel.add(new JLabel("2 players are already playing… try again later"));
        JOptionPane.showConfirmDialog(null, panel, "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    private void handleSuccessfulDisconnection() {
        connectionController.disconnect();
        connectionController.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(true);
        connectionController.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(false);
        connectionController.getGui().getWordlePanel().setVisible(false);
    }

    private void handleSuccessfulConnection() {
        connectionController.getGui().getMyMenuBar().getMenuConnectTo().setEnabled(false);
        connectionController.getGui().getMyMenuBar().getMenuDisconnect().setEnabled(true);
    }

    private void handleNames(OpponentNameMessage opponentNameMessage) {
        connectionController.getGui().getWordlePanel().getOpponentPanel().getNameTextField().setText(opponentNameMessage.getName());
    }

    private void handleGameState(GameStateMessage gameStateMessage) {
        connectionController.getGui().updateWithGameStateData(gameStateMessage.getGameStateData());
    }
}

