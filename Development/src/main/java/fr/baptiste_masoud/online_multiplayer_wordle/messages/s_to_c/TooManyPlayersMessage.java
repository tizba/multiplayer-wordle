package fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c;

public class TooManyPlayersMessage extends ServerToClientMessage {
    public TooManyPlayersMessage() {
        super(ServerToClientMessageType.TOO_MANY_PLAYERS);
    }
}
