package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SubmissionErrorMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.Player;
import fr.baptiste_masoud.multiplayer_wordle.server.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Game {
    private static final Random random = new Random();
    private final Player[] players = new Player[2];
    private final Server server;
    private final HashMap<UUID, Boolean> wantsToContinue;
    private final List<String> words;
    private boolean running = false;
    private Round round;

    public Game(Server server, List<String> words) {
        this.server = server;
        this.words = words;

        // init wantsToContinue HashMap
        this.wantsToContinue = new HashMap<>();
    }

    private static String getRandomWord(List<String> words) {
        return words.get(random.nextInt(words.size()));
    }

    public void setWantsToContinue(Player player, boolean wantsToContinue) {
        this.wantsToContinue.put(player.getUuid(), wantsToContinue);

        if (this.wantsToContinue.get(player.getUuid()) && this.wantsToContinue.get(getOpponentOf(player).getUuid())) {
            this.startNewRound();
        }

    }

    private void startNewRound() {
        for (Player player : players)
            this.wantsToContinue.put(player.getUuid(), false);

        this.round = new Round(players, getRandomWord(words));
    }

    public void start() {
        startNewRound();
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
        } else throw new GameIsFullException("Can’t add a player to a game that is full !");

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
        boolean playerWantsToContinue = (round != null) &&
                wantsToContinue.get(player.getUuid());

        RoundData roundData = (round != null) ? round.getRoundData(player) : null;

        return new GameStateData(this.running, !this.isFullOfPlayers(), playerWantsToContinue, roundData);
    }

    /**
     * @param player the player for which we need his opponent
     * @return the opponent of the player
     */
    public Player getOpponentOf(Player player) {
        return player == players[0] ? players[1] : players[0];
    }

    /**
     * Sends a SuccessfulDisconnectionMessage to all players
     */
    public void disconnectPlayers() {
        try {
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
        } catch (NullPointerException ignored) {
            // empty
        }

    }

    /**
     * Sends the name of player to his opponent if it has one
     *
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
     *
     * @param player        the player that has submitted
     * @param submittedWord the word that the player submitted
     */
    public void addSubmission(Player player, String submittedWord) {
        // if a round has started and word is in list of valid words
        if (round != null && isWordValid(submittedWord)) {
            this.round.addSubmission(player, submittedWord);
        }
        // if not, sends an error
        else {
            player.getMessageSender().sendMessage(new SubmissionErrorMessage());
        }
    }

    public boolean isWordValid(String word) {
        return this.words.contains(word);
    }
}
