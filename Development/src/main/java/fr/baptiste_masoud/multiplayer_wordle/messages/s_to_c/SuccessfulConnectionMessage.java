package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

public class SuccessfulConnectionMessage extends ServerToClientMessage {
    public SuccessfulConnectionMessage() {
        super(ServerToClientMessageType.SUCCESSFUL_CONNECTION);
    }
}
