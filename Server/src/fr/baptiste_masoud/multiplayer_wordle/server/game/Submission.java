package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.LetterValidity;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

public class Submission {
    private final String wordToDiscover;
    private final String submittedWord;
    private final LetterValidity[] validity;
    private final boolean correct;

    public Submission(String wordToDiscover, String submittedWord) {
        assert (wordToDiscover.length() == submittedWord.length());
        this.wordToDiscover = wordToDiscover.toUpperCase();
        this.submittedWord = submittedWord.toUpperCase();
        this.validity = initValidity(this.wordToDiscover, this.submittedWord);
        this.correct = checkIfCorrect(validity);
    }

    public SubmissionData getSubmissionData() {
        return new SubmissionData(submittedWord, validity, correct);
    }

    public SubmissionData getOpponentSubmissionData() {
        return new SubmissionData(null, validity, correct);
    }

    private LetterValidity[] initValidity(String wordToDiscover, String submittedWord) {
        LetterValidity[] validity = new LetterValidity[submittedWord.length()];

        // find all in place characters
        for (int i = 0; i < validity.length; i++) {
            char currentLetter = submittedWord.toCharArray()[i];
            if (currentLetter == wordToDiscover.toCharArray()[i]) {
                validity[i] = LetterValidity.IN_PLACE;
            } else {
                validity[i] = null;
            }

        }

        String notInPlaceWordToDiscover = wordToDiscover, notInPlaceSubmittedWord = submittedWord;
        for (int i = 0; i < validity.length; i++) {
            if(validity[i] == LetterValidity.IN_PLACE) {
                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);

                chars = notInPlaceWordToDiscover.toCharArray();
                chars[i] = '%';
                notInPlaceWordToDiscover = String.valueOf(chars);
            }
        }

        for (int i = 0; i < validity.length; i++) {
            if (notInPlaceSubmittedWord.toCharArray()[i] == '%')
                continue;

            int indexOfCurrentChar = notInPlaceWordToDiscover.indexOf(notInPlaceSubmittedWord.toCharArray()[i]);
            if (indexOfCurrentChar == -1) {
                validity[i] = LetterValidity.NOT_IN_WORD;

                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);

            } else {
                validity[i] = LetterValidity.IN_WORD;

                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);


                chars = notInPlaceWordToDiscover.toCharArray();
                chars[indexOfCurrentChar] = '%';
                notInPlaceWordToDiscover = String.valueOf(chars);
            }
        }

        return validity;
    }

    private boolean checkIfCorrect(LetterValidity[] submissionValidity) {
        for (LetterValidity letterValidity: submissionValidity) {
            if (letterValidity != LetterValidity.IN_PLACE) return false;
        }
        return true;
    }

    public LetterValidity[] getValidity() {
        return validity;
    }

    public String getSubmittedWord() {
        return submittedWord;
    }

    public String getWordToDiscover() {
        return wordToDiscover;
    }

    public boolean isCorrect() {
        return correct;
    }
}
