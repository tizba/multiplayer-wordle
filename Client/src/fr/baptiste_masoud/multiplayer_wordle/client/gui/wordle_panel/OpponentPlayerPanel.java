package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

public class OpponentPlayerPanel extends PlayerPanel {
    public OpponentPlayerPanel(GUIController guiController) {
        super(guiController);
        nameTextField.setEditable(false);
        nameTextField.setText("Opponent name");
        submitTextField.setVisible(false);
    }

    @Override
    public void updateWithGameState(RoundData roundData) {
        // update submissions
        submissionsPanel.removeAll();
        for (SubmissionData submission: roundData.opponentSubmissions()) {
            submissionsPanel.addSubmission(submission);
        }
    }
}