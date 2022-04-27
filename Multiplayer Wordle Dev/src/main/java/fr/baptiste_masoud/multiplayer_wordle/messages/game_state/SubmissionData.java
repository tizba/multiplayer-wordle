package fr.baptiste_masoud.multiplayer_wordle.messages.game_state;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public record SubmissionData(String submittedWord,
                             LetterValidity[] submissionValidity,
                             boolean correct)
        implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionData that = (SubmissionData) o;
        return correct == that.correct && submittedWord.equals(that.submittedWord) && Arrays.equals(submissionValidity, that.submissionValidity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(submittedWord, correct);
        result = 31 * result + Arrays.hashCode(submissionValidity);
        return result;
    }

    @Override
    public String toString() {
        return "SubmissionData{" +
                "submittedWord='" + submittedWord + '\'' +
                ", submissionValidity=" + Arrays.toString(submissionValidity) +
                ", correct=" + correct +
                '}';
    }
}
