package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

import fr.baptiste_masoud.multiplayer_wordle.messages.Message;
import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public abstract class ClientToServerMessage extends Message {

    public ClientToServerMessage(MessageType messageType) {
        super(messageType);
    }
}
