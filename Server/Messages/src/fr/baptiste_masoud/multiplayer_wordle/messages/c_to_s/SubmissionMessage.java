package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

import java.io.Serializable;

public class SubmissionMessage extends ClientToServerMessage implements Serializable {
    private final String submittedWord;

    public SubmissionMessage(String submittedWord) {
        super(MessageType.SUBMISSION);
        this.submittedWord = submittedWord;
    }

    public String getSubmittedWord() {
        return submittedWord;
    }
}
