package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

public class SubmissionErrorMessage extends ServerToClientMessage {

    public SubmissionErrorMessage() {
        super(ServerToClientMessageType.SUBMISSION_ERROR);
    }
}
