package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class DisconnectMessage extends ClientToServerMessage {
    public DisconnectMessage() {
        super(MessageType.DISCONNECT);
    }
}
