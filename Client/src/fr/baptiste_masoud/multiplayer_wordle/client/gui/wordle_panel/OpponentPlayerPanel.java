package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;

public class OpponentPlayerPanel extends PlayerPanel {
    public OpponentPlayerPanel(GUIController guiController) {
        super(guiController);
        this.getNameTextField().setEditable(false);
        this.getNameTextField().setText("Opponent name");
    }
}
