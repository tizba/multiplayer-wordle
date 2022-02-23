package fr.baptiste_masoud.multiplayer_wordle.messages;

public class TooManyPlayersMessage extends Message {

    public TooManyPlayersMessage() {
        super(MessageType.TOO_MANY_PLAYERS);
    }
}
