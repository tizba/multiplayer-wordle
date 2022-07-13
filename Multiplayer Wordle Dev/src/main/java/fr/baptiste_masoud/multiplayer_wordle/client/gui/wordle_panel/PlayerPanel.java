package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.TimerTask;

public class PlayerPanel extends JPanel {
    protected final SubmissionsPanel submissionsPanel;
    protected final NameTextField nameTextField;
    protected final SubmitTextField submitTextField;
    protected final JButton continueButton;

    public PlayerPanel(ConnectionController connectionController) {

        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints;

        // init nameTextField
        this.nameTextField = new NameTextField(connectionController);
        // place nameTextField
        constraints = new GridBagConstraints();
        constraints.weighty = 0.1;
        constraints.weightx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.ipady = constraints.ipadx = 20;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(nameTextField, constraints);

        // init SubmissionsPanel
        this.submissionsPanel = new SubmissionsPanel();
        // place SubmissionsPanel
        constraints = new GridBagConstraints();
        constraints.gridy = 1;
        constraints.weighty = 0.9;
        constraints.weightx = 1;
        constraints.gridheight = 6;
        constraints.fill = GridBagConstraints.BOTH;
        add(submissionsPanel, constraints);

        // init submitTextField
        this.submitTextField = new SubmitTextField(connectionController);
        // place submitTextField
        constraints = new GridBagConstraints();
        constraints.gridy = 7;
        constraints.weighty = 0.1;
        constraints.weightx = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(submitTextField, constraints);

        // init continueButton
        this.continueButton = new ContinueButton(connectionController);
        this.continueButton.setVisible(false);
        // place continueButton
        constraints = new GridBagConstraints();
        constraints.gridy = 8;
        constraints.weighty = 0.1;
        constraints.weightx = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(continueButton, constraints);
    }

    public NameTextField getNameTextField() {
        return nameTextField;
    }

    public void updateWithGameState(GameStateData gameStateData) {
        // update submissions
        submissionsPanel.removeAll();
        if (gameStateData.currentRound() != null) {
            for (SubmissionData submission : gameStateData.currentRound().playerSubmissions()) {
                submissionsPanel.addSubmission(submission);
            }

            submitTextField.setVisible(!gameStateData.currentRound().playerHasFinished());

            this.continueButton.setVisible(gameStateData.currentRound().playerHasFinished()
                    && gameStateData.currentRound().opponentHasFinished());
        }

        if (gameStateData.playerWantsToContinue())
            continueButton.setVisible(false);

    }

    public void handleError() {
        this.submitTextField.setBorder(new LineBorder(Color.red));
        new java.util.Timer().schedule(new ErrorColorTask(this.submitTextField), 2000);
    }

    private static class ErrorColorTask extends TimerTask {
        private final SubmitTextField submitTextField;

        private ErrorColorTask(SubmitTextField submitTextField) {
            this.submitTextField = submitTextField;
        }

        @Override
        public void run() {
            this.submitTextField.setBorder(new LineBorder(Color.gray));
        }
    }
}
