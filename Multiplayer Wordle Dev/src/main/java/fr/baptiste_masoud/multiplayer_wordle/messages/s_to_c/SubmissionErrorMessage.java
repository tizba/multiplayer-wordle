package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

public class SubmissionErrorMessage extends ServerToClientMessage {
    private final String error;

    public SubmissionErrorMessage(String error) {
        super(ServerToClientMessageType.SUBMISSION_ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
