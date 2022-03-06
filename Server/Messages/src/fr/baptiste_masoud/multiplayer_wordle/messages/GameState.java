package fr.baptiste_masoud.multiplayer_wordle.messages;

import java.io.Serializable;

public class GameState implements Serializable {
    private final boolean running;

    public GameState(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
}
