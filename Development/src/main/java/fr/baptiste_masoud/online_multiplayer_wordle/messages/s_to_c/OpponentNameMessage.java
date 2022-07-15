package fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c;

public class OpponentNameMessage extends ServerToClientMessage {
    private final String name;

    public OpponentNameMessage(String name) {
        super(ServerToClientMessageType.OPPONENT_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
