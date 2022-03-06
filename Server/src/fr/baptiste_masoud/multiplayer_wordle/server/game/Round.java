package fr.baptiste_masoud.multiplayer_wordle.server.game;

public class Round {
    private final Submission[] player1Submissions = new Submission[6];
    private final Submission[] player2Submissions = new Submission[6];
    private String wordToDiscover;

    public Submission[] getPlayer1Submissions() {
        return player1Submissions;
    }

    public Submission[] getPlayer2Submissions() {
        return player2Submissions;
    }

    public String getWordToDiscover() {
        return wordToDiscover;
    }
}
