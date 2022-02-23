package fr.baptiste_masoud.multiplayer_wordle.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
