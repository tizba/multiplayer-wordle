package fr.baptiste_masoud.multiplayer_wordle.messages;

public enum MessageType {
    DISCONNECT,
    SUBMISSION,
    SET_NAME,
    GAME_STATE_DATA,
    TOO_MANY_PLAYERS,
    SUCCESSFUL_CONNECTION,
    SUCCESSFUL_DISCONNECTION,
    OPPONENT_NAME,
    SUBMISSION_ERROR
}
