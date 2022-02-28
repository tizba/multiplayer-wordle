package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.Message;
import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class TooManyPlayersMessage extends ServerToClientMessage {
    public TooManyPlayersMessage() {
        super(MessageType.TOO_MANY_PLAYERS);
    }
}
