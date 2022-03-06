package fr.baptiste_masoud.multiplayer_wordle.server.game;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.LetterValidity;

public class Submission {
    private final String wordToDiscover;
    private final String submittedWord;
    private final LetterValidity[] submissionValidity;
    private final boolean correct;

    public Submission(String wordToDiscover, String submittedWord) {
        assert (wordToDiscover.length() == submittedWord.length());
        this.wordToDiscover = wordToDiscover.toUpperCase();
        this.submittedWord = submittedWord.toUpperCase();
        this.submissionValidity = initSubmissionValidity();
        this.correct = checkIfCorrect(submissionValidity);
    }

    private LetterValidity[] initSubmissionValidity() {
        LetterValidity[] submissionValidity = new LetterValidity[submittedWord.length()];


        // find all in place characters
        for (int i = 0; i < submissionValidity.length; i++) {
            char currentLetter = submittedWord.toCharArray()[i];
            if (currentLetter == wordToDiscover.toCharArray()[i]) {
                submissionValidity[i] = LetterValidity.IN_PLACE;
            } else {
                submissionValidity[i] = null;
            }

        }

        String notInPlaceWordToDiscover = wordToDiscover, notInPlaceSubmittedWord = submittedWord;
        for (int i = 0; i < submissionValidity.length; i++) {
            if(submissionValidity[i] == LetterValidity.IN_PLACE) {
                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);

                chars = notInPlaceWordToDiscover.toCharArray();
                chars[i] = '%';
                notInPlaceWordToDiscover = String.valueOf(chars);
            }
        }

        for (int i = 0; i < submissionValidity.length; i++) {
            if (notInPlaceSubmittedWord.toCharArray()[i] == '%')
                continue;

            int indexOfCurrentChar = notInPlaceWordToDiscover.indexOf(notInPlaceSubmittedWord.toCharArray()[i]);
            if (indexOfCurrentChar == -1) {
                submissionValidity[i] = LetterValidity.NOT_IN_WORD;

                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);

            } else {
                submissionValidity[i] = LetterValidity.IN_WORD;

                char[] chars = notInPlaceSubmittedWord.toCharArray();
                chars[i] = '%';
                notInPlaceSubmittedWord = String.valueOf(chars);


                chars = notInPlaceWordToDiscover.toCharArray();
                chars[indexOfCurrentChar] = '%';
                notInPlaceWordToDiscover = String.valueOf(chars);
            }
        }

        return submissionValidity;
    }

    private boolean checkIfCorrect(LetterValidity[] submissionValidity) {
        for (LetterValidity letterValidity: submissionValidity) {
            if (letterValidity != LetterValidity.IN_PLACE) return false;
        }
        return true;
    }

    public LetterValidity[] getSubmissionValidity() {
        return submissionValidity;
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
