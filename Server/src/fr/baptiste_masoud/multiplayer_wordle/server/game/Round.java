package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;
import fr.baptiste_masoud.multiplayer_wordle.server.PlayerClient;

public class Round {
    private final Submission[] player1Submissions = new Submission[6];
    private final PlayerClient player1;
    private int player1NextSubmissionIndex = 0;
    private boolean player1HasFinished = false;

    private final Submission[] player2Submissions = new Submission[6];
    private int player2NextSubmissionIndex = 0;
    private final PlayerClient player2;
    private boolean player2HasFinished = false;

    private final String wordToDiscover;


    public Round(PlayerClient player1, PlayerClient player2, String wordToDiscover) {
        this.player1 = player1;
        this.player2 = player2;
        this.wordToDiscover = wordToDiscover;
    }

    public Submission[] getPlayerSubmissions(PlayerClient playerClient) {
        if (playerClient == player1) return player1Submissions;
        else return player2Submissions;
    }

    public String getWordToDiscover() {
        return wordToDiscover;
    }

    public boolean playerHasFinished(PlayerClient playerClient) {
        if (playerClient == player1) return player1HasFinished;
        else return player2HasFinished;
    }

    public boolean areSubmissionsFull(PlayerClient playerClient) {
        if (playerClient == player1) return player1NextSubmissionIndex >= player1Submissions.length;
        else return player2NextSubmissionIndex >= player2Submissions.length;
    }

    /**
     * @param player the player that will receive the RoundData
     * @return the RoundData that can be sent to player
     */
    public RoundData getRoundData(PlayerClient player) {
        SubmissionData[] playerSubmissions, opponentSubmissions;
        if (player == player1) {
            playerSubmissions = new SubmissionData[player1Submissions.length];
            for (int i = 0; i < playerSubmissions.length; i++) {
                if (player1Submissions[i] != null)
                    playerSubmissions[i] = player1Submissions[i].getSubmissionData();
            }

            opponentSubmissions = new SubmissionData[player2Submissions.length];
            for (int i = 0; i < opponentSubmissions.length; i++) {
                if (player2Submissions[i] != null)
                    opponentSubmissions[i] = player2Submissions[i].getOpponentSubmissionData();
            }
        } else {
            playerSubmissions = new SubmissionData[player2Submissions.length];
            for (int i = 0; i < playerSubmissions.length; i++) {
                if (player2Submissions[i] != null)
                    playerSubmissions[i] = player2Submissions[i].getSubmissionData();
            }

            opponentSubmissions = new SubmissionData[player1Submissions.length];
            for (int i = 0; i < opponentSubmissions.length; i++) {
                if (player1Submissions[i] != null)
                    opponentSubmissions[i] = player1Submissions[i].getOpponentSubmissionData();
            }
        }

        boolean playerHasFinished, opponentHasFinished;
        if (player == player1) {
            playerHasFinished = player1HasFinished;
            opponentHasFinished = player2HasFinished;
        } else {
            playerHasFinished = player2HasFinished;
            opponentHasFinished = player1HasFinished;
        }

        return new RoundData(playerHasFinished, opponentHasFinished, playerSubmissions, opponentSubmissions);
    }

    public void addSubmission(PlayerClient player, String submittedWord) {
        if (player == player1) {
            player1Submissions[player1NextSubmissionIndex++] = new Submission(this.wordToDiscover, submittedWord);
        } else if (player == player2) {
            player2Submissions[player2NextSubmissionIndex++] = new Submission(this.wordToDiscover, submittedWord);
        }
        updatePlayersFinished();
    }

    private void updatePlayersFinished() {
        // check if player1 has finished
        player1HasFinished =(player1NextSubmissionIndex > 0 &&
                (areSubmissionsFull(player1) || player1Submissions[player1NextSubmissionIndex-1].isCorrect()));

        // check if player2 has finished
        player2HasFinished = player2NextSubmissionIndex > 0 &&
                (areSubmissionsFull(player2) || player2Submissions[player2NextSubmissionIndex - 1].isCorrect());
    }
}
