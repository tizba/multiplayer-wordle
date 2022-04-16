package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

import java.io.Serializable;

public record RoundData(boolean playerHasFinished, boolean opponentHasFinished, SubmissionData[] playerSubmissions,
                        SubmissionData[] opponentSubmissions) implements Serializable {
}
