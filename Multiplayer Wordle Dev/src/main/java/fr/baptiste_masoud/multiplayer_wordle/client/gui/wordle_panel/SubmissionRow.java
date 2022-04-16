package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.LetterValidity;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SubmissionRow extends JPanel {
    private final JLabel[] submissionLabels;

    /**
     * @param submittedWord the submitted text to be displayed, if null, it does not display any letters
     * @param submissionValidity the validity of each letter
     */
    public SubmissionRow(@Nullable String submittedWord, @Nullable LetterValidity[] submissionValidity) {
        this.setLayout(new GridLayout(1, 6, 5, 0));
        setBackground(Color.lightGray);

        this.submissionLabels = new JLabel[6];

        setAndAddSubmissionLabels(submittedWord);
        setLabelsColors(submissionValidity);
    }
    
    private void setAndAddSubmissionLabels(String submittedWord) {
        // if submittedWord is null
        if (submittedWord == null) {
            for (int i = 0; i < submissionLabels.length; i++) {
                submissionLabels[i] = new JLabel("%", SwingConstants.CENTER);
                submissionLabels[i].setBackground(Color.gray.brighter().brighter());
                submissionLabels[i].setForeground(submissionLabels[i].getBackground());
                submissionLabels[i].setFont(new Font("Arial", Font.PLAIN, 28));
                submissionLabels[i].setOpaque(true);
                add(submissionLabels[i]);
            }
        } else {
            int i = 0;
            for (char character : submittedWord.toCharArray()) {
                submissionLabels[i] = new JLabel(String.valueOf(character), SwingConstants.CENTER);
                submissionLabels[i].setOpaque(true);
                submissionLabels[i].setBackground(Color.red);
                submissionLabels[i].setFont(new Font("Arial", Font.PLAIN, 28));
                add(submissionLabels[i]);
                i++;
            }
        }
    }

    private void setLabelsColors(LetterValidity[] submissionValidity) {
        if (submissionValidity != null) {
            for (int i = 0; i < submissionValidity.length; i++) {
                Color backgroundColor = Color.gray.brighter();
                switch (submissionValidity[i]) {
                    case IN_WORD -> backgroundColor = Color.orange;
                    case IN_PLACE -> backgroundColor = Color.green.darker();
                }
                submissionLabels[i].setBackground(backgroundColor);

                if (Objects.equals(submissionLabels[i].getText(), "%"))
                    submissionLabels[i].setForeground(submissionLabels[i].getBackground());
            }
        }
    }
}
