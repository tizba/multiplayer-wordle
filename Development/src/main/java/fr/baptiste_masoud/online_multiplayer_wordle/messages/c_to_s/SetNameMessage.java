package fr.baptiste_masoud.online_multiplayer_wordle.messages.c_to_s;

public class SetNameMessage extends ClientToServerMessage {
    private final String name;

    public SetNameMessage(String name) {
        super(ClientToServerMessageType.SET_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
