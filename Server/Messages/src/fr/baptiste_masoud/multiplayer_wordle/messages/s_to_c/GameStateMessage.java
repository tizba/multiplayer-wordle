package fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c;

import fr.baptiste_masoud.multiplayer_wordle.messages.GameState;
import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;

public class GameStateMessage extends ServerToClientMessage {
    private final GameState gameState;

    public GameStateMessage(GameState gameState) {
        super(MessageType.GAME_STATE);
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
