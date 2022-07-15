package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;

public class GameStateMessage extends ServerToClientMessage {
    private final GameStateData gameStateData;

    public GameStateMessage(GameStateData gameStateData) {
        super(ServerToClientMessageType.GAME_STATE_DATA);
        this.gameStateData = gameStateData;
    }

    public GameStateData getGameStateData() {
        return gameStateData;
    }
}
