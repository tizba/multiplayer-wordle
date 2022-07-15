package fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c;

public enum ServerToClientMessageType {
    GAME_STATE_DATA,
    TOO_MANY_PLAYERS,
    SUCCESSFUL_CONNECTION,
    SUCCESSFUL_DISCONNECTION,
    OPPONENT_NAME,
    SUBMISSION_ERROR
}
