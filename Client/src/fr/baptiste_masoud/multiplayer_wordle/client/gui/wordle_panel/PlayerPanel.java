package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private final GUIController guiController;
    private final SubmissionsPanel submissionsPanel;
    private final NameTextField nameTextField;

    public PlayerPanel(GUIController guiController) {
        this.guiController = guiController;

        this.setLayout(new GridBagLayout());

        GridBagConstraints c;

        // init nameTextField
        this.nameTextField = new NameTextField(guiController);
        // place nameTextField
        c = new GridBagConstraints();
        c.weighty = 0.1;
        c.weightx = 1;
        c.gridy = 0;
        c.ipady = c.ipadx = 20;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(nameTextField, c);

        // init SubmissionsPanel
        this.submissionsPanel = new SubmissionsPanel();
        // place SubmissionsPanel
        c = new GridBagConstraints();
        c.gridy = 1;
        c.weighty = 0.9;
        c.weightx = 1;
        c.gridheight = 6;
        c.fill = GridBagConstraints.BOTH;
        add(submissionsPanel, c);
    }

    public NameTextField getNameTextField() {
        return nameTextField;
    }
}
