package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.LetterValidity;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

public class Submission {
    private final String submittedWord;
    private final LetterValidity[] validity;
    private final boolean correct;

    public Submission(String wordToDiscover, String submittedWord) {
        if (wordToDiscover.length() != submittedWord.length())
            throw new SubmissionLengthException(wordToDiscover + " and " + submittedWord + " are not the same length");
        String wordToDiscover1 = wordToDiscover.toUpperCase();
        this.submittedWord = submittedWord.toUpperCase();
        this.validity = initValidity(wordToDiscover1, this.submittedWord);
        this.correct = checkIfCorrect(validity);
    }

    public SubmissionData getSubmissionData() {
        return new SubmissionData(submittedWord, validity, correct);
    }

    public SubmissionData getOpponentSubmissionData() {
        return new SubmissionData(null, validity, correct);
    }

    private LetterValidity[] initValidity(String wordToDiscover, String submittedWord) {
        @SuppressWarnings("SpellCheckingInspection") LetterValidity[] letterValidities = new LetterValidity[submittedWord.length()];

        // find all in place characters
        for (int i = 0; i < letterValidities.length; i++) {
            char currentLetter = submittedWord.toCharArray()[i];
            if (currentLetter == wordToDiscover.toCharArray()[i]) {
                letterValidities[i] = LetterValidity.IN_PLACE;
            } else {
                letterValidities[i] = null;
            }

        }

        String notInPlaceWordToDiscover = wordToDiscover;
        String notInPlaceSubmittedWord = submittedWord;

        for (int i = 0; i < letterValidities.length; i++) {
            if (letterValidities[i] == LetterValidity.IN_PLACE) {
                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);

                chars = notInPlaceWordToDiscover.toCharArray();
                chars[i] = '%';
                notInPlaceWordToDiscover = String.valueOf(chars);
            }
        }

        for (int i = 0; i < letterValidities.length; i++) {
            if (notInPlaceSubmittedWord.toCharArray()[i] == '%')
                continue;

            int indexOfCurrentChar = notInPlaceWordToDiscover.indexOf(notInPlaceSubmittedWord.toCharArray()[i]);
            if (indexOfCurrentChar == -1) {
                letterValidities[i] = LetterValidity.NOT_IN_WORD;

                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);

            } else {
                letterValidities[i] = LetterValidity.IN_WORD;

                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);


                chars = notInPlaceWordToDiscover.toCharArray();
                chars[indexOfCurrentChar] = '%';
                notInPlaceWordToDiscover = String.valueOf(chars);
            }
        }

        return letterValidities;
    }

    private boolean checkIfCorrect(LetterValidity[] submissionValidity) {
        for (LetterValidity letterValidity: submissionValidity) {
            if (letterValidity != LetterValidity.IN_PLACE) return false;
        }
        return true;
    }

    public boolean isCorrect() {
        return correct;
    }
}
