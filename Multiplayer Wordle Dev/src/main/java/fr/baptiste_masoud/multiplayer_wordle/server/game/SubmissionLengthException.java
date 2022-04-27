package fr.baptiste_masoud.multiplayer_wordle.server.game;

public class SubmissionLengthException extends IllegalArgumentException {
    public SubmissionLengthException(String message) {
        super(message);
    }
}
