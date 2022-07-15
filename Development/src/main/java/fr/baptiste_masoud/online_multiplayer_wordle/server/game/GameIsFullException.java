package fr.baptiste_masoud.online_multiplayer_wordle.server.game;

class GameIsFullException extends RuntimeException {

    public GameIsFullException(String message) {
        super(message);
    }
}
