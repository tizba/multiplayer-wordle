package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

import javax.swing.border.EmptyBorder;

public class OpponentPlayerPanel extends PlayerPanel {
    public OpponentPlayerPanel(ConnectionController connectionController) {
        super(connectionController);
        nameTextField.setEditable(false);
        nameTextField.setText("Opponent name");
        submitTextField.setOpaque(false);
        submitTextField.setBorder(new EmptyBorder(0, 0,0,0));
        submitTextField.setEnabled(false);
        submissionErrorLabel.setVisible(true);
    }

    @Override
    public void updateWithGameState(RoundData roundData) {
        // update submissions
        submissionsPanel.removeAll();
        for (SubmissionData submission: roundData.opponentSubmissions()) {
            submissionsPanel.addSubmission(submission);
        }

        if (roundData.playerHasFinished()) {
            submitTextField.setVisible(false);
            submissionErrorLabel.setVisible(false);
        } else {
            submitTextField.setVisible(true);
            submissionErrorLabel.setVisible(true);
        }
    }
}