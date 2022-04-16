package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.ServerToClientMessage;

public class SuccessfulDisconnectionMessage extends ServerToClientMessage {
    public SuccessfulDisconnectionMessage() {
        super(MessageType.SUCCESSFUL_DISCONNECTION);
    }
}
