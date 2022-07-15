package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

import javax.swing.*;
import java.awt.*;

public class SubmissionsPanel extends JPanel {

    public SubmissionsPanel() {
        setLayout(new GridLayout(6, 1, 0, 10));
    }

    public void addSubmission(SubmissionData submission) {
        if (submission == null) {
            this.add(new SubmissionRow(null, null));
        } else {
            this.add(new SubmissionRow(submission.submittedWord(), submission.submissionValidity()));
        }

    }

}
