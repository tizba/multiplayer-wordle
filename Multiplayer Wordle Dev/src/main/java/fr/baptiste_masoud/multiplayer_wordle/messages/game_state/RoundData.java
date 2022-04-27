package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public record RoundData(boolean playerHasFinished,
                        boolean opponentHasFinished,
                        SubmissionData[] playerSubmissions,
                        SubmissionData[] opponentSubmissions)
        implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundData roundData = (RoundData) o;
        return playerHasFinished == roundData.playerHasFinished && opponentHasFinished == roundData.opponentHasFinished && Arrays.equals(playerSubmissions, roundData.playerSubmissions) && Arrays.equals(opponentSubmissions, roundData.opponentSubmissions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(playerHasFinished, opponentHasFinished);
        result = 31 * result + Arrays.hashCode(playerSubmissions);
        result = 31 * result + Arrays.hashCode(opponentSubmissions);
        return result;
    }

    @Override
    public String toString() {
        return "RoundData{" +
                "playerHasFinished=" + playerHasFinished +
                ", opponentHasFinished=" + opponentHasFinished +
                ", playerSubmissions=" + Arrays.toString(playerSubmissions) +
                ", opponentSubmissions=" + Arrays.toString(opponentSubmissions) +
                '}';
    }
}
