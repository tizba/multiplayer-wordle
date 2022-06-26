package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
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
    public void updateWithGameState(GameStateData gameStateData) {
        // update submissions
        submissionsPanel.removeAll();
        for (SubmissionData submission: gameStateData.currentRound().opponentSubmissions()) {
            submissionsPanel.addSubmission(submission);
        }

        if (gameStateData.currentRound().playerHasFinished()) {
            submitTextField.setVisible(false);
            submissionErrorLabel.setVisible(false);
        } else {
            submitTextField.setVisible(true);
            submissionErrorLabel.setVisible(true);
        }
    }
}