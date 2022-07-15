package fr.baptiste_masoud.online_multiplayer_wordle.messages.c_to_s;

public class DisconnectMessage extends ClientToServerMessage {
    public DisconnectMessage() {
        super(ClientToServerMessageType.DISCONNECT);
    }
}
