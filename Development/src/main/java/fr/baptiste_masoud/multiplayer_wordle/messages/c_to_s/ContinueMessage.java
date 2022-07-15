package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

public class ContinueMessage extends ClientToServerMessage {

    public ContinueMessage() {
        super(ClientToServerMessageType.CONTINUE);
    }
}
