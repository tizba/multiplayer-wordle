package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SubmissionErrorMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.Player;
import fr.baptiste_masoud.multiplayer_wordle.server.Server;


public class Game {
    private final Player[] players = new Player[2];
    private final Server server;
    private boolean running = false;

    private Round round;

    public Game(Server server) {
        this.server = server;
    }

    public void start() {
        this.round = new Round(players, "Should");
        this.running = true;
    }

    public void end() {
        this.running = false;
        this.disconnectPlayers();
        server.initNewGame();
    }

    public boolean isFullOfPlayers() {
        return players[0] != null && players[1] != null;
    }

    public void addPlayer(Player player) {
        if (players[0] == null) {
            players[0] = player;
        } else if (players[1] == null) {
            players[1] = player;
        } else throw new RuntimeException("Can’t add a player to a game that is full !");

        player.acceptConnection();

        if (isFullOfPlayers()) {
            this.start();
        }

        sendGameStateMessage();

    }

    /**
     * Sends a GameStateMessage to all players in the game
     */
    public void sendGameStateMessage() {
        if (players[0] != null)
            players[0].getMessageSender().sendMessage(this.getGameStateMessage(players[0]));
        if (players[1] != null)
            players[1].getMessageSender().sendMessage(this.getGameStateMessage(players[1]));
    }

    /**
     * @param player the player that needs the GameStateData
     * @return the GameStateMessage to be sent to the player
     */
    public GameStateMessage getGameStateMessage(Player player) {
        return new GameStateMessage(getGameStateData(player));
    }

    /**
     * @param player the player that needs the GameStateData
     * @return the GameStateData that needs to be sent to the PlayerClient parameter via a GameStateMessage
     */
    private GameStateData getGameStateData(Player player) {
        RoundData roundData = (round != null) ? round.getRoundData(player) : null;
        return new GameStateData(this.running, roundData);
    }

    /**
     * Sends a SuccessfulDisconnectionMessage to all players
     */
    public void disconnectPlayers() {
        this.running = false;
        if (players[0] != null) {
            players[0].getMessageSender().sendMessage(new SuccessfulDisconnectionMessage());
            players[0].setConnected(false);
            players[0] = null;
        }
        if (players[1] != null) {
            players[1].getMessageSender().sendMessage(new SuccessfulDisconnectionMessage());
            players[1].setConnected(false);
            players[1] = null;
        }
    }

    /**
     * Sends the name of player to his opponent if it has one
     * @param player the Player whose name needs to be sent to his opponent
     */
    public void sendNameToOpponent(Player player) {
        if (player == players[0] && players[1] != null) {
            players[1].getMessageSender().sendMessage(new OpponentNameMessage(player.getName()));
        } else if (player == players[1] && players[0] != null) {
            players[0].getMessageSender().sendMessage(new OpponentNameMessage(player.getName()));
        }
    }

    /**
     * This method checks if the submission is authorized, if yes, it adds it to the Game,
     * if not, it sends back to the player a SubmissionError containing the error that made the submission unauthorized
     * @param player the player that has submitted
     * @param submittedWord the word that the player submitted
     */
    public void addSubmission(Player player, String submittedWord) {
        // if a round has started
        // and the player has not finished yet
        // and the submitted word has the same length as the word to discover
        if (round != null
                && !round.didPlayerFinished(player)
                && submittedWord.length() == round.getWordToDiscover().length()) {
            this.round.addSubmission(player, submittedWord);
        }
        // if not, detect the error and sends it
        else {
            String error = "Error";
            if (round == null)
                error = "The round has not started yet";
            else if (round.didPlayerFinished(player))
                error = "You have finished this round";
            else if (submittedWord.length() != round.getWordToDiscover().length())
                error = "Impossible to submit \"" + submittedWord + "\" because it is not " + round.getWordToDiscover().length() + " letters long";

            player.getMessageSender().sendMessage(new SubmissionErrorMessage(error));
        }
    }
}
