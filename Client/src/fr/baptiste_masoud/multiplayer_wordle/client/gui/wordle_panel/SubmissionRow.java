package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.LetterValidity;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class SubmissionRow extends JPanel {
    private final JLabel[] submissionLabels;

    /**
     * @param submittedWord the submitted text to be displayed, if null, it does not display any letters
     * @param submissionValidity
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

                if (submissionLabels[i].getText() == "%")
                    submissionLabels[i].setForeground(submissionLabels[i].getBackground());
            }
        }
    }
    
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//
//        // to maximize letters font size
//        // https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size
//        for (JLabel submissionLabel : submissionLabels) {
//            Font labelFont = submissionLabel.getFont();
//            String labelText = submissionLabel.getText();
//
//            int stringWidth = submissionLabel.getFontMetrics(labelFont).stringWidth(labelText);
//            int componentWidth = submissionLabel.getWidth();
//
//            // find out how much the font can grow in width.
//            double widthRatio = (double) componentWidth / (double) stringWidth;
//            // reduce the font size a bit
//            widthRatio /= 2;
//
//            int newFontSize = (int) (labelFont.getSize() * widthRatio);
//            int componentHeight = submissionLabel.getHeight();
//
//            // pick a new font size, so it will not be larger than the height of label.
//            int fontSizeToUse = Math.min(newFontSize, componentHeight);
//
//            // set the label's font size to the newly determined size.
//            submissionLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
//        }
//    }
}
