package fr.baptiste_masoud.online_multiplayer_wordle.server.game;

import fr.baptiste_masoud.online_multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.online_multiplayer_wordle.messages.game_state.SubmissionData;
import fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c.SubmissionErrorMessage;
import fr.baptiste_masoud.online_multiplayer_wordle.server.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class Round {
    private final String wordToDiscover;
    private final Player[] players;
    private final HashMap<UUID, Submission[]> submissions;
    private final HashMap<UUID, Boolean> playersFinished;


    public Round(Player[] players, String wordToDiscover) {
        this.players = players;
        this.wordToDiscover = wordToDiscover;

        // init submissions HashMap
        this.submissions = new HashMap<>();
        this.submissions.put(players[0].getUuid(), new Submission[6]);
        this.submissions.put(players[1].getUuid(), new Submission[6]);

        // init playersFinished HashMap
        this.playersFinished = new HashMap<>();
        this.playersFinished.put(players[0].getUuid(), false);
        this.playersFinished.put(players[1].getUuid(), false);
    }

    /**
     * @param player the player for which we need his submissions
     * @return the submissions of the player
     */
    public Submission[] getPlayerSubmissions(Player player) {
        return submissions.get(player.getUuid());
    }

    /**
     * @param player the player for which we need his opponent
     * @return the opponent of the player
     */
    public Player getOpponentOf(Player player) {
        return player == players[0] ? players[1] : players[0];
    }

    public String getWordToDiscover() {
        return wordToDiscover;
    }

    public boolean didPlayerFinished(Player player) {
        return playersFinished.get(player.getUuid());
    }

    /**
     * @param player the player that will receive the RoundData
     * @return the RoundData that can be sent to player
     */
    public RoundData getRoundData(Player player) {
        Submission[] playerSubmissions = getPlayerSubmissions(player);
        SubmissionData[] playerSubmissionsData = new SubmissionData[playerSubmissions.length];
        for (int i = 0; i < playerSubmissionsData.length; i++) {
            if (playerSubmissions[i] != null)
                playerSubmissionsData[i] = playerSubmissions[i].getSubmissionData();
        }

        Submission[] opponentSubmissions = getPlayerSubmissions(getOpponentOf(player));
        SubmissionData[] opponentSubmissionsData = new SubmissionData[opponentSubmissions.length];
        for (int i = 0; i < opponentSubmissionsData.length; i++) {
            if (opponentSubmissions[i] != null)
                opponentSubmissionsData[i] = opponentSubmissions[i].getOpponentSubmissionData();
        }


        boolean playerHasFinished = didPlayerFinished(player);
        boolean opponentHasFinished = didPlayerFinished(getOpponentOf(player));


        return new RoundData(playerHasFinished, opponentHasFinished, playerSubmissionsData, opponentSubmissionsData);
    }

    /**
     * @param player the player for which we need its next Submission Index
     * @return the index of the next submission of player or -1 if he can not submit anymore
     */
    private int nextSubmissionIndexOfPlayer(Player player) {
        Submission[] playerSubmissions = getPlayerSubmissions(player);
        for (int i = 0; i < playerSubmissions.length; i++) {
            if (playerSubmissions[i] == null) return i;
        }
        return -1;
    }

    public void addSubmission(Player player, String submittedWord) {
        // if the player has not finished yet
        // and the submitted word has the same length as the word to discover
        // and the submitted word is a word without special chars
        if (!this.didPlayerFinished(player)
                && submittedWord.length() == this.getWordToDiscover().length()
                && Pattern.matches("[A-Za-z]*", submittedWord)) {
            getPlayerSubmissions(player)[nextSubmissionIndexOfPlayer(player)] = new Submission(wordToDiscover, submittedWord);
            updatePlayersFinished();
        } else {
            player.getMessageSender().sendMessage(new SubmissionErrorMessage());
        }
    }

    private void updatePlayersFinished() {
        // a player has finished if he has already submitted the maximum amount of times
        // or if he has already submitted once and his last submission is correct

        // check if players[0] has finished
        int playerNextSubmissionIndex = nextSubmissionIndexOfPlayer(players[0]);
        boolean playerHasFinished = (playerNextSubmissionIndex == -1
                || (playerNextSubmissionIndex != 0 && getPlayerSubmissions(players[0])[playerNextSubmissionIndex - 1].isCorrect()));
        playersFinished.put(players[0].getUuid(), playerHasFinished);

        // check if players[1] has finished
        playerNextSubmissionIndex = nextSubmissionIndexOfPlayer(players[1]);
        playerHasFinished = (playerNextSubmissionIndex == -1
                || (playerNextSubmissionIndex != 0 && getPlayerSubmissions(players[1])[playerNextSubmissionIndex - 1].isCorrect()));
        playersFinished.put(players[1].getUuid(), playerHasFinished);
    }
}
