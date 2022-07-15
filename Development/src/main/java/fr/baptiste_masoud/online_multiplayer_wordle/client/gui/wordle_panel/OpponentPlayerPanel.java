package fr.baptiste_masoud.online_multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.online_multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.online_multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.online_multiplayer_wordle.messages.game_state.SubmissionData;

import javax.swing.border.EmptyBorder;

public class OpponentPlayerPanel extends PlayerPanel {
    public OpponentPlayerPanel(ConnectionController connectionController) {
        super(connectionController);
        nameTextField.setEditable(false);
        nameTextField.setText("Opponent name");
        submitTextField.setOpaque(false);
        submitTextField.setBorder(new EmptyBorder(2, 2, 2, 2));
        submitTextField.setEnabled(false);
    }

    @Override
    public void updateWithGameState(GameStateData gameStateData) {
        // update submissions
        submissionsPanel.removeAll();
        if (gameStateData.currentRound() != null) {
            for (SubmissionData submission : gameStateData.currentRound().opponentSubmissions()) {
                submissionsPanel.addSubmission(submission);
            }

            submitTextField.setVisible(!gameStateData.currentRound().playerHasFinished());
        }

    }
}