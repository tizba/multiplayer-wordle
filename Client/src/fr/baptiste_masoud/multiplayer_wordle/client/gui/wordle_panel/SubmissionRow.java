package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import javax.swing.*;
import java.awt.*;

public class SubmissionRow extends JPanel {
    private final String submission;

    private final JLabel[] submissionLabels;

    /**
     *
     * @param submission the submitted text to be displayed, if a char in the String is equal to '%', it displays an empty cell
     */
    public SubmissionRow(String submission) {
        this.submission = submission.toUpperCase();
        this.submissionLabels = new JLabel[submission.length()];

        this.setLayout(new GridLayout(1, submission.length(), 5, 0));
        setBackground(Color.lightGray);
        setAndAddSubmissionLabels();
    }

    public void setAndAddSubmissionLabels() {
        int i = 0;
        for (char character : submission.toCharArray()) {
            submissionLabels[i] = new JLabel(String.valueOf(character), SwingConstants.CENTER);
            submissionLabels[i].setOpaque(true);
            submissionLabels[i].setBackground(Color.gray);
            submissionLabels[i].setFont(new Font("Arial", Font.PLAIN, 28));

            if (character == '%') {
                submissionLabels[i].setForeground(submissionLabels[i].getBackground());
            }

            add(submissionLabels[i]);
            i++;

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
