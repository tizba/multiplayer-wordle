package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulConnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;

public class Game {
    private PlayerClient playerClient1;
    private PlayerClient playerClient2;
    private final Server server;

    public Game(Server server) {
        this.server = server;
    }

    public PlayerClient getPlayerClient1() {
        return playerClient1;
    }

    public PlayerClient getPlayerClient2() {
        return playerClient2;
    }

    public Server getServer() {
        return server;
    }

    public void addPlayerClient(PlayerClient playerClient) {
        playerClient.setGame(this);
        if (playerClient1 == null) {
            playerClient1 = playerClient;
            return;
        }
        if (playerClient2 == null) {
            playerClient2 = playerClient;
            return;
        }
    }

    public void disconnectPlayerClients() {
        if (playerClient1 != null) {
            playerClient1.getMessageSender().sendMessage(new SuccessfulDisconnectionMessage());
            playerClient1.setConnected(false);
        }
        if (playerClient2 != null) {
            playerClient2.getMessageSender().sendMessage(new SuccessfulDisconnectionMessage());
            playerClient2.setConnected(false);
        }
    }
}
