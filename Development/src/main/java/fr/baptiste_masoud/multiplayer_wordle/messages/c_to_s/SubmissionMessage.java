package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

import java.io.Serializable;

public class SubmissionMessage extends ClientToServerMessage implements Serializable {
    private final String submittedWord;

    public SubmissionMessage(String submittedWord) {
        super(ClientToServerMessageType.SUBMISSION);
        this.submittedWord = submittedWord;
    }

    public String getSubmittedWord() {
        return submittedWord;
    }
}
