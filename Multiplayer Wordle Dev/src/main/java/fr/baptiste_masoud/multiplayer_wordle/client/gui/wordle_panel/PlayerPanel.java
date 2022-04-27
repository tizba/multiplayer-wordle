package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    protected final SubmissionsPanel submissionsPanel;
    protected final NameTextField nameTextField;
    protected final JTextField submitTextField;
    protected final JLabel submissionErrorLabel;

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

        // init submissionErrorLabel
        this.submissionErrorLabel = new JLabel("");
        this.submissionErrorLabel.setVisible(false);
        this.submissionErrorLabel.setForeground(Color.red.darker());
        this.submissionErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.submissionErrorLabel.setVerticalAlignment(SwingConstants.CENTER);
        // place submissionErrorLabel
        constraints = new GridBagConstraints();
        constraints.gridy = 8;
        constraints.weighty = 0.1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(submissionErrorLabel, constraints);
    }

    public NameTextField getNameTextField() {
        return nameTextField;
    }

    public void updateWithGameState(RoundData roundData) {
        // update submissions
        submissionsPanel.removeAll();
        for (SubmissionData submission: roundData.playerSubmissions()) {
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

    public void setSubmissionErrorLabelText(String error) {
        this.submissionErrorLabel.setVisible(true);
        this.submissionErrorLabel.setText(error);
    }
}
