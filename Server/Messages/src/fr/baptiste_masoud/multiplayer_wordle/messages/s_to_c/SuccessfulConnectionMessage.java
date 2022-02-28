package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.ServerToClientMessage;

public class SuccessfulConnectionMessage extends ServerToClientMessage {
    public SuccessfulConnectionMessage() {
        super(MessageType.SUCCESSFUL_CONNECTION);
    }
}
