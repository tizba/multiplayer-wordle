package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

import java.io.Serializable;

public class GameStateData implements Serializable {
    private final boolean running;
    private final RoundData currentRound;

    public GameStateData(boolean running, RoundData currentRound) {
        this.running = running;
        this.currentRound = currentRound;
    }

    public boolean isRunning() {
        return running;
    }
}
