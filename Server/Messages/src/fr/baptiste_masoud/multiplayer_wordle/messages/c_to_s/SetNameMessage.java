package fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class SetNameMessage extends ClientToServerMessage {
    private final String name;

    public SetNameMessage(String name) {
        super(MessageType.SET_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
