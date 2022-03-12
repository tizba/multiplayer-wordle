package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.RoundData;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.SubmissionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerPanel extends JPanel {
    protected final SubmissionsPanel submissionsPanel;
    protected final NameTextField nameTextField;
    protected final JTextField submitTextField;
    private final JLabel submissionErrorLabel;

    public PlayerPanel(GUIController guiController) {

        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints;

        // init nameTextField
        this.nameTextField = new NameTextField(guiController);
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
        this.submitTextField = new SubmitTextField(guiController);
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
        this.submissionErrorLabel.setHorizontalAlignment(JLabel.CENTER);
        this.submissionErrorLabel.setVerticalAlignment(JLabel.CENTER);
        // place submissionErrorLabel
        constraints = new GridBagConstraints();
        constraints.gridy = 8;
        constraints.weighty = 0.05;
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
        }

    }

    public JLabel getSubmissionErrorLabel() {
        return submissionErrorLabel;
    }

    public void setSubmissionErrorLabelText(String error) {
        this.submissionErrorLabel.setVisible(true);
        this.submissionErrorLabel.setText(error);
    }
}
