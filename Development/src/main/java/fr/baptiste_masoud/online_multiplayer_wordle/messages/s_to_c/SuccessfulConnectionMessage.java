package fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c;

public class SuccessfulConnectionMessage extends ServerToClientMessage {
    public SuccessfulConnectionMessage() {
        super(ServerToClientMessageType.SUCCESSFUL_CONNECTION);
    }
}
