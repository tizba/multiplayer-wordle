package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

public class SubmissionData {
    private final String submittedWord;
    private final LetterValidity[] submissionValidity;
    private final boolean correct;

    public SubmissionData(String submittedWord, LetterValidity[] submissionValidity, boolean correct) {
        this.submittedWord = submittedWord;
        this.submissionValidity = submissionValidity;
        this.correct = correct;
    }


    public LetterValidity[] getSubmissionValidity() {
        return submissionValidity;
    }

    public String getSubmittedWord() {
        return submittedWord;
    }

    public boolean isCorrect() {
        return correct;
    }
}
