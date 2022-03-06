package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.GameState;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.PlayerClient;
import fr.baptiste_masoud.multiplayer_wordle.server.Server;
import fr.baptiste_masoud.multiplayer_wordle.server.game.Round;


public class Game {
    private PlayerClient playerClient1;
    private PlayerClient playerClient2;
    private final Server server;
    private boolean running = false;

    private int nbRounds = 6;
    private Round[] rounds = new Round[nbRounds];

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

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public void addPlayerClient(PlayerClient playerClient) {
        playerClient.setGame(this);
        if (playerClient1 == null) {
            playerClient1 = playerClient;
        } else if (playerClient2 == null) {
            playerClient2 = playerClient;
        } else return;

        if (playerClient1 != null && playerClient2 != null)
            this.running = true;

        sendGameStateMessage();
    }

    public void sendGameStateMessage() {
        GameStateMessage gameStateMessage = getGameStateMessage();
        if (playerClient1 != null)
            playerClient1.getMessageSender().sendMessage(gameStateMessage);
        if (playerClient2 != null)
            playerClient2.getMessageSender().sendMessage(gameStateMessage);
    }

    public GameStateMessage getGameStateMessage() {
        GameState gameState = new GameState(this.running);
        return new GameStateMessage(gameState);
    }

    public void disconnectPlayerClients() {
        this.running = false;
        if (playerClient1 != null) {
            playerClient1.getMessageSender().sendMessage(new SuccessfulDisconnectionMessage());
            playerClient1.setConnected(false);
        }
        if (playerClient2 != null) {
            playerClient2.getMessageSender().sendMessage(new SuccessfulDisconnectionMessage());
            playerClient2.setConnected(false);
        }

        sendGameStateMessage();
    }

    /**
     * Sends the name of playerClient to his opponent.
     * @param playerClient the PlayerClient whose name needs to be sent to opponent
     */
    public void sendNameToOpponent(PlayerClient playerClient) {
        if (playerClient == playerClient1 && playerClient2 != null) {
            playerClient2.getMessageSender().sendMessage(new OpponentNameMessage(playerClient.getPlayerName()));
        } else if (playerClient == playerClient2 && playerClient1 != null) {
            playerClient1.getMessageSender().sendMessage(new OpponentNameMessage(playerClient.getPlayerName()));
        }

    }
}
