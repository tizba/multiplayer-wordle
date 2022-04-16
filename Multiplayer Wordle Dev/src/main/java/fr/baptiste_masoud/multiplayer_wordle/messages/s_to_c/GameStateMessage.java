package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class GameStateMessage extends ServerToClientMessage {
    private final GameStateData gameStateData;

    public GameStateMessage(GameStateData gameStateData) {
        super(MessageType.GAME_STATE_DATA);
        this.gameStateData = gameStateData;
    }

    public GameStateData getGameStateData() {
        return gameStateData;
    }
}
