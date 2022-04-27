package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

import java.io.Serializable;

public abstract class ClientToServerMessage implements Serializable {
    private final ClientToServerMessageType messageType;

    protected ClientToServerMessage(ClientToServerMessageType messageType) {
        this.messageType = messageType;
    }

    public ClientToServerMessageType getMessageType() {
        return messageType;
    }
}
