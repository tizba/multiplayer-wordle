package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

public class RoundData {
    private final SubmissionData[] player1Submissions;
    private final SubmissionData[] player2Submissions;


    public RoundData(SubmissionData[] player1Submissions, SubmissionData[] player2Submissions) {
        this.player1Submissions = player1Submissions;
        this.player2Submissions = player2Submissions;
    }
}
