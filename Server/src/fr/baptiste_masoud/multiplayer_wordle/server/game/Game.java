package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SubmissionErrorMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.PlayerClient;
import fr.baptiste_masoud.multiplayer_wordle.server.Server;
import org.jetbrains.annotations.Nullable;


public class Game {
    private PlayerClient playerClient1;
    private PlayerClient playerClient2;
    private final Server server;
    private boolean running = false;

    @Nullable
    private Round round;

    // when both players are connected, this method initialize the game and launch it
    public void init() {
        round = new Round(playerClient1, playerClient2, "Should");
        setRunning(true);
    }

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

    public void addPlayerClient(PlayerClient playerClient) {
        playerClient.setGame(this);
        if (playerClient1 == null) {
            playerClient1 = playerClient;
        } else if (playerClient2 == null) {
            playerClient2 = playerClient;
        } else return;

        // if both players are connected, init the game
        if (playerClient2 != null)
            this.init();

        sendGameStateMessage();
    }

    public void sendGameStateMessage() {
        if (playerClient1 != null)
            playerClient1.getMessageSender().sendMessage(getGameStateMessage(playerClient1));
        if (playerClient2 != null)
            playerClient2.getMessageSender().sendMessage(getGameStateMessage(playerClient2));
    }

    /**
     * @param player the player that needs the GameStateData
     * @return the GameStateMessage that needs to be sent to the PlayerClient parameter
     */
    public GameStateMessage getGameStateMessage(PlayerClient player) {
        return new GameStateMessage(getGameStateData(player));
    }

    /**
     * @param player the player that needs the GameStateData
     * @return the GameStateData that needs to be sent to the PlayerClient parameter via a GameStateMessage
     */
    private GameStateData getGameStateData(PlayerClient player) {
        RoundData roundData = (round != null) ? round.getRoundData(player) : null;

        return new GameStateData(this.running, roundData);
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

    /**
     * This method checks if the submission is valid / authorized, if yes, it adds it to the Game,
     * if not, it sends back to the player a SubmissionError
     * @param player the player that submitted
     * @param submittedWord the word that the player submitted
     */
    public void addSubmission(PlayerClient player, String submittedWord) {
        if (round != null && !round.areSubmissionsFull(player) && submittedWord.length() == round.getWordToDiscover().length()) {
            this.round.addSubmission(player, submittedWord);
        }
        else {
            player.getMessageSender().sendMessage(new SubmissionErrorMessage("Error TBD"));
        }
    }
}
