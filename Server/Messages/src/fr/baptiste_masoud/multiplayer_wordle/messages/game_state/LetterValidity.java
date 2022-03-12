package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

import java.io.Serializable;

public enum LetterValidity implements Serializable {
    IN_PLACE,
    IN_WORD,
    NOT_IN_WORD
}
