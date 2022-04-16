package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class OpponentNameMessage extends ServerToClientMessage {
    private final String name;

    public OpponentNameMessage(String name) {
        super(MessageType.OPPONENT_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
