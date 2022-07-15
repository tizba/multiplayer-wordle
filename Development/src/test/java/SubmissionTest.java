import fr.baptiste_masoud.online_multiplayer_wordle.messages.game_state.LetterValidity;
import fr.baptiste_masoud.online_multiplayer_wordle.server.game.Submission;
import fr.baptiste_masoud.online_multiplayer_wordle.server.game.SubmissionLengthException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest {
    @Test
    void totallyInvalidTest() {
        Submission submission = new Submission("majeur", "voyons");
        assertFalse(submission.isCorrect());
        for (LetterValidity validity : submission.getValidity()) {
            assertEquals(LetterValidity.NOT_IN_WORD, validity);
        }
    }

    @Test
    void inWordTest() {
        Submission submission = new Submission("majeur", "copies");
        assertFalse(submission.isCorrect());

        LetterValidity[] expectedValidity = {LetterValidity.NOT_IN_WORD, LetterValidity.NOT_IN_WORD,
                LetterValidity.NOT_IN_WORD, LetterValidity.NOT_IN_WORD,
                LetterValidity.IN_WORD, LetterValidity.NOT_IN_WORD};

        assertArrayEquals(expectedValidity, submission.getValidity());
    }

    @Test
    void inPlaceTest() {
        Submission submission = new Submission("majeur", "mineur");
        assertFalse(submission.isCorrect());

        LetterValidity[] expectedValidity = {LetterValidity.IN_PLACE, LetterValidity.NOT_IN_WORD,
                LetterValidity.NOT_IN_WORD, LetterValidity.IN_PLACE,
                LetterValidity.IN_PLACE, LetterValidity.IN_PLACE};

        assertArrayEquals(expectedValidity, submission.getValidity());
    }

    @Test
    void totallyCorrectTest() {
        Submission submission = new Submission("majeur", "majeur");
        assertTrue(submission.isCorrect());

        LetterValidity[] expectedValidity = new LetterValidity[6];
        Arrays.fill(expectedValidity, LetterValidity.IN_PLACE);

        assertArrayEquals(expectedValidity, submission.getValidity());
    }

    @Test
    void throwsLengthException() {
        assertThrows(SubmissionLengthException.class, () -> {
            Submission submission = new Submission("majeur", "majeurs");
        });

        assertThrows(SubmissionLengthException.class, () -> {
            Submission submission = new Submission("majeur", "majeu");
        });
    }
}