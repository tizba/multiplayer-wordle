package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class GameStateMessage extends ServerToClientMessage {
    private final GameStateData gameState;

    public GameStateMessage(GameStateData gameState) {
        super(MessageType.GAME_STATE);
        this.gameState = gameState;
    }

    public GameStateData getGameState() {
        return gameState;
    }
}
