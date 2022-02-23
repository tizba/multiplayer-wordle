package fr.baptiste_masoud.multiplayer_wordle.server;

public class Game {
    private final PlayerClient playerClient1;
    private final PlayerClient playerClient2;

    public Game(PlayerClient playerClient1, PlayerClient playerClient2) {
        this.playerClient1 = playerClient1;
        this.playerClient2 = playerClient2;
    }
}
