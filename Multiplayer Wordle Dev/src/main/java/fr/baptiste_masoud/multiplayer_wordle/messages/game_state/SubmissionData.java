package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

import java.io.Serializable;

public record SubmissionData(String submittedWord,
                             LetterValidity[] submissionValidity,
                             boolean correct)
        implements Serializable {
}
