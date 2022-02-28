package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.Message;
import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public abstract class ServerToClientMessage extends Message {

    public ServerToClientMessage(MessageType messageType) {
        super(messageType);
    }
}
