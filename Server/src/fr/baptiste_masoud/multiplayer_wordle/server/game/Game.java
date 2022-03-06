package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.LetterValidity;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.OpponentNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.SuccessfulDisconnectionMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.PlayerClient;
import fr.baptiste_masoud.multiplayer_wordle.server.Server;


public class Game {
    private PlayerClient playerClient1;
    private PlayerClient playerClient2;
    private final Server server;
    private boolean running = false;

    private Round currentRound = new Round();

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

        if (playerClient2 != null)
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
        return new GameStateMessage(getGameStateData());
    }

    private GameStateData getGameStateData() {
        // init submissionsData
        SubmissionData[] player1SubmissionsData = new SubmissionData[currentRound.getPlayer1Submissions().length];
        for (int i = 0; i < currentRound.getPlayer1Submissions().length; i++) {
            Submission submission = currentRound.getPlayer1Submissions()[i];
            String submittedWord = submission.getSubmittedWord();
            LetterValidity[] submissionValidity = submission.getSubmissionValidity();
            boolean isCorrect = submission.isCorrect();

            player1SubmissionsData[i] = new SubmissionData(submittedWord, submissionValidity, isCorrect);
        }

        SubmissionData[] player2SubmissionsData = new SubmissionData[currentRound.getPlayer2Submissions().length];
        for (int i = 0; i < currentRound.getPlayer2Submissions().length; i++) {
            Submission submission = currentRound.getPlayer2Submissions()[i];
            String submittedWord = submission.getSubmittedWord();
            LetterValidity[] submissionValidity = submission.getSubmissionValidity();
            boolean isCorrect = submission.isCorrect();

            player2SubmissionsData[i] = new SubmissionData(submittedWord, submissionValidity, isCorrect);
        }

        RoundData roundData = new RoundData(player1SubmissionsData, player2SubmissionsData);

        GameStateData gameStateData = new GameStateData(this.running, roundData);
        return gameStateData;
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
