package fr.baptiste_masoud.multiplayer_wordle.server.game;

class GameIsFullException extends RuntimeException {

    public GameIsFullException(String message) {
        super(message);
    }
}
