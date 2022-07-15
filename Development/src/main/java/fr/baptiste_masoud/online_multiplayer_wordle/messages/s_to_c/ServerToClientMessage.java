package fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c;

import java.io.Serializable;

public abstract class ServerToClientMessage implements Serializable {
    private final ServerToClientMessageType messageType;

    protected ServerToClientMessage(ServerToClientMessageType messageType) {
        this.messageType = messageType;
    }

    public ServerToClientMessageType getMessageType() {
        return messageType;
    }
}
